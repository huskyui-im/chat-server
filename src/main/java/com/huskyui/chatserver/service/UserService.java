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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class UserService {

    @Resource
    private RemoteCacheUtils remoteCacheUtils;

    private static final ConcurrentHashMap<String/*channelId*/,Channel/*Channel*/> channelMap = new ConcurrentHashMap<>();

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public User getUserInfoByToken(String token) {
        String userName = remoteCacheUtils.get(token);
        if (StringUtils.isEmpty(userName)){
            return null;
        }
        return new User(userName);
    }

    public void addChannel(Channel channel){
        String channelId = channel.id().asLongText();
        channelMap.put(channelId,channel);
        log.info("用户 {} 登录，远程地址 {}",channel.attr(AttrConstants.USER_ID).get(),channel.remoteAddress());
    }

    public void removeChannel(Channel channel){
        String channelId = channel.id().asLongText();
        channelMap.remove(channelId);
        log.info("用户 {} 离开，远程地址 {}",channel.attr(AttrConstants.USER_ID).get(),channel.remoteAddress());
    }

    public void groupPush(String msg){
        channelGroup.writeAndFlush(new TextWebSocketFrame(msg));
    }

    public void addMsg(String group,String msg){
        remoteCacheUtils.sendMsgToGroup(group,msg);
    }

    public void groupPushHistoryMsg(String groupName,Channel channel){
        List<String> historyMessageList = remoteCacheUtils.historyMessageList(groupName);
        if (!CollectionUtils.isEmpty(historyMessageList)){
            for (String msg : historyMessageList) {
                channel.writeAndFlush(new TextWebSocketFrame(msg));
            }
        }
    }


    public void createGroup(String groupName) {
        // create zset groupName
        remoteCacheUtils.createGroup(groupName);
    }
}
