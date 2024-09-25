package com.huskyui.chatserver.websocket.handler;

import com.huskyui.chatserver.websocket.common.AttrConstants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
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
            String uri = httpRequest.uri();
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
            Map<String, List<String>> parameters = queryStringDecoder.parameters();
            List<String> tokenList = parameters.get("token");
            String token = null;
            if (!CollectionUtils.isEmpty(tokenList)){
                token = tokenList.get(0);
            }
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
