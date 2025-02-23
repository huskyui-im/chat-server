package com.huskyui.chatserver.websocket.handler;

import com.huskyui.chatserver.model.User;
import com.huskyui.chatserver.service.UserService;
import com.huskyui.chatserver.websocket.common.AttrConstants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {
    private final UserService userService;

    public AuthHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest){
            // check request
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            String uri = httpRequest.uri();
            log.info("uri:{}",uri);
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
            Map<String, List<String>> parameters = queryStringDecoder.parameters();
            List<String> tokenList = parameters.get("token");
            String token = null;
            if (!CollectionUtils.isEmpty(tokenList)){
                token = tokenList.get(0);
            }
            log.info("token:{}",token);
            User user = getUserIdByToken(token);
            if (user == null){
                sendUnauthorizedResponse(ctx);
                return;
            }
            Channel channel = ctx.channel();
            channel.attr(AttrConstants.USER_ID).set(user.getUsername());
            userService.addChannel(channel);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userService.removeChannel(ctx.channel());
        super.channelInactive(ctx);
    }

    private User getUserIdByToken(String token) {
        return userService.getUserInfoByToken(token);
    }


    private void sendUnauthorizedResponse(ChannelHandlerContext ctx) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                io.netty.handler.codec.http.HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
        response.headers().set("Content-Type", "text/plain");
        ctx.writeAndFlush(response).addListener(future -> ctx.close());
    }

}
