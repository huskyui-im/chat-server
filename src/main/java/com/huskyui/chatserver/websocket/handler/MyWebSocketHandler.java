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
        handleWebSocketFrame(ctx, o);

    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, Object o) {
        if (o instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) o;
            String userId = ctx.channel().attr(AttrConstants.USER_ID).get();
            String text = textWebSocketFrame.text();
            Message message = JsonUtils.stringToObject(text, new TypeReference<Message>() {
            });
            if (message == null) {
                return;
            }
            if (message.getOpType() == OpTypeConstants.JOIN_GROUP) {
                userService.joinGroup(message.getGroup(), ctx.channel());
                userService.groupPushHistoryMsg(message.getGroup(), ctx.channel());
            } else if (message.getOpType() == OpTypeConstants.SEND_MSG) {
                message.setMessage(message.getMessage());
                message.setSendUser(userId);
                String msg = JsonUtils.objectToJson(message);
                userService.groupPush(message.getGroup(),msg);
                userService.addMsg(message.getGroup(), msg);
            } else if (message.getOpType() == OpTypeConstants.CREATE_GROUP) {
                userService.createGroup(message.getGroup());
            }else if (message.getOpType() == OpTypeConstants.SEND_IMAGE){
                message.setMessage(message.getMessage());
                message.setSendUser(userId);
                String msg = JsonUtils.objectToJson(message);
                userService.groupPush(message.getGroup(),msg);
                userService.addMsg(message.getGroup(),msg);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}