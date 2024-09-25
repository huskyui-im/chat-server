package com.huskyui.chatserver.websocket.handler;

import com.huskyui.chatserver.websocket.common.AttrConstants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.Map;

public class MyWebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame o) throws Exception {
        handleWebSocketFrame(ctx,o);

    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, Object o) {
        if (o instanceof TextWebSocketFrame){
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) o;
            String userId = ctx.channel().attr(AttrConstants.USER_ID).get();
            ctx.channel().writeAndFlush(new TextWebSocketFrame("收到"+userId+"发送的"+textWebSocketFrame.text()));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}