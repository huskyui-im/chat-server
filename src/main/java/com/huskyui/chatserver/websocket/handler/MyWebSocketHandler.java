package com.huskyui.chatserver.websocket.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huskyui.chatserver.model.Message;
import com.huskyui.chatserver.service.UserService;
import com.huskyui.chatserver.utils.JsonUtils;
import com.huskyui.chatserver.websocket.common.AttrConstants;
import com.huskyui.chatserver.websocket.common.OpTypeConstants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.Map;

public class MyWebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private UserService userService;

    public MyWebSocketHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame o) throws Exception {
        handleWebSocketFrame(ctx,o);

    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, Object o) {
        if (o instanceof TextWebSocketFrame){
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) o;
            String userId = ctx.channel().attr(AttrConstants.USER_ID).get();
            String text = textWebSocketFrame.text();
            Message message = JsonUtils.stringToObject(text, new TypeReference<Message>() {
            });
            if (message == null){
                return;
            }
            if (message.getOpType() == OpTypeConstants.JOIN_GROUP) {
                userService.groupPushHistoryMsg(message.getMessage(),ctx.channel());
            }else if(message.getOpType() == OpTypeConstants.SEND_MSG){
                message.setMessage(String.format("%s:%s", userId, message.getMessage()));
                String msg = JsonUtils.objectToJson(message);
                userService.groupPush(msg);
                userService.addMsg("group1",msg);
            }else if (message.getOpType() == OpTypeConstants.CREATE_GROUP){
                userService.createGroup(message.getGroup());
            }


        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}