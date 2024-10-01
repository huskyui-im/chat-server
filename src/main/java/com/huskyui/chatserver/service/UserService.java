package com.huskyui.chatserver.service;

import com.google.common.collect.ImmutableMap;
import com.huskyui.chatserver.model.User;
import com.huskyui.chatserver.utils.RemoteCacheUtils;
import com.huskyui.chatserver.websocket.common.AttrConstants;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class UserService {

    @Resource
    private RemoteCacheUtils remoteCacheUtils;

    private static final ConcurrentHashMap<String/*channelId*/,Channel/**/>




    public User getUserInfoByToken(String token) {
        String userName = remoteCacheUtils.get(token);
        if (StringUtils.isEmpty(userName)){
            return null;
        }
        return new User(userName);
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
