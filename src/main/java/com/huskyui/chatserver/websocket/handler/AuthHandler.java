package com.huskyui.chatserver.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;

public class AuthHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest){
            // check request
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            System.out.println(httpRequest.uri());
            for (Map.Entry<String, String> header : httpRequest.headers()) {
                System.out.println("header:"+header.getKey() + ":" + header.getValue());
            }
        }



        super.channelRead(ctx, msg);
    }
}
