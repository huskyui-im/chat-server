package com.huskyui.chatserver.service;

import com.google.common.collect.ImmutableMap;
import com.huskyui.chatserver.model.User;
import com.huskyui.chatserver.websocket.common.AttrConstants;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final static ImmutableMap<String/*token*/, User/*用户信息*/> tokenInfoMap =
            ImmutableMap.of(
                    "123",new User("huskyui"),
                    "456",new User("zz")
            );

    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public User getUserInfoByToken(String token) {
        return tokenInfoMap.getOrDefault(token, null);
    }

    public void addChannel(Channel channel){
        channelGroup.add(channel);
        log.info("用户 {} 登录，远程地址 {}",channel.attr(AttrConstants.USER_ID).get(),channel.remoteAddress());
    }

    public void removeChannel(Channel channel){
        channelGroup.remove(channel);
        log.info("用户 {} 离开，远程地址 {}",channel.attr(AttrConstants.USER_ID).get(),channel.remoteAddress());
    }

    public void groupPush(String msg){
        channelGroup.writeAndFlush(new TextWebSocketFrame(msg));
    }


}
