package com.huskyui.chatserver.service;

import com.google.common.collect.ImmutableMap;
import com.huskyui.chatserver.domain.ChatUser;
import com.huskyui.chatserver.mapper.ChatUserMapper;
import com.huskyui.chatserver.model.Result;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class UserService {

    @Resource
    private RemoteCacheUtils remoteCacheUtils;

    @Resource
    private ChatUserMapper chatUserMapper;

    private static final ConcurrentHashMap<String/*channelId*/,Channel/*Channel*/> channelMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String/*groupId*/,ChannelGroup/*ChannelGroup*/> groupMap = new ConcurrentHashMap<>();


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

    public void groupPush(String groupName,String msg){
        ChannelGroup channels = groupMap.get(groupName);
        if (channels == null){
            return;
        }
        channels.writeAndFlush(new TextWebSocketFrame(msg));
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

    public void joinGroup(String groupName, Channel channel) {
        groupMap.compute(groupName,(k,v)->{
            if (v == null){
                v = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            }
            v.add(channel);
            return v;
        });
    }

    public Result getUserInfoByUserId(String userId) {
        ChatUser chatUser = chatUserMapper.selectByUsername(userId);
        if (chatUser == null){
            return Result.fail(1001,"用户信息不存在");
        }
        ImmutableMap<String,String> userInfo = ImmutableMap.of(
                "id",chatUser.getId().toString(),
                "username",chatUser.getUsername(),
                "avatar",chatUser.getAvatar());
        return Result.ok(userInfo);
    }
}
