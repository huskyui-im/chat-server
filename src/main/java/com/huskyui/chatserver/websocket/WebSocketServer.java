package com.huskyui.chatserver.websocket;

import com.huskyui.chatserver.websocket.handler.AuthHandler;
import com.huskyui.chatserver.websocket.handler.MyWebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WebSocketServer {

    @Bean
    public void initWebSocketServer(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec()); // HTTP 协议解析，用于握手阶段
                            pipeline.addLast(new HttpObjectAggregator(65536));// HTTP 协议解析，用于握手阶段
                            pipeline.addLast(new AuthHandler());
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true,65536*10,false,true)); // WebSocket 握手、控制帧处理
                            pipeline.addLast(new MyWebSocketHandler());
                        }
                    });
            ChannelFuture f = b.bind(8888).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
