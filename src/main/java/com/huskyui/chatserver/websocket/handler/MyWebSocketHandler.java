package com.huskyui.chatserver.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.Map;

public class MyWebSocketHandler extends SimpleChannelInboundHandler<Object> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        if (o instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest)o;
            HttpHeaders headers = request.headers();
            for (Map.Entry<String, String> header : headers) {
                System.out.println("header:"+header.getKey() + ":" + header.getValue());
            }
        }else if (o instanceof  WebSocketFrame){
            handleWebSocketFrame(ctx,o);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            String queryString = uri.split("\\?")[1]; // 获取查询参数部分
            // 处理查询参数，例如解析key=value形式
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";
                // 处理键值对
            }
        }
        super.channelRead(ctx, msg);
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, Object o) {
        if (o instanceof TextWebSocketFrame){
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) o;
            if (o instanceof  FullHttpRequest){

            }
            ctx.channel().writeAndFlush(new TextWebSocketFrame("收到"+textWebSocketFrame.text()));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}