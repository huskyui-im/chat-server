package com.huskyui.chatserver.websocket.handler;

import com.huskyui.chatserver.websocket.common.AttrConstants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    private final static Map<String,String> tokenMap = new HashMap<String,String>(){{
       put("abc","huskyui");
       put("def","zz");
    }};


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest){
            // check request
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            HttpHeaders headers = httpRequest.headers();
            String token = headers.get("token");
            String userId = getUserIdByToken(token);
            if (StringUtils.isEmpty(userId)){
                sendUnauthorizedResponse(ctx);
                return;
            }
            Channel channel = ctx.channel();
            channel.attr(AttrConstants.USER_ID).set(userId);
        }
        super.channelRead(ctx, msg);
    }

    private String getUserIdByToken(String token) {
        return tokenMap.get(token);
    }


    private void sendUnauthorizedResponse(ChannelHandlerContext ctx) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                io.netty.handler.codec.http.HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
        response.headers().set("Content-Type", "text/plain");
        ctx.writeAndFlush(response).addListener(future -> ctx.close());
    }

}
