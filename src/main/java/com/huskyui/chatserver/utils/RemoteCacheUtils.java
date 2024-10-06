package com.huskyui.chatserver.utils;

import io.lettuce.core.Range;
import io.lettuce.core.SetArgs;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XAddArgs;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RemoteCacheUtils {
    @Resource
    private RedisCommands<String, String> redisCommands;


    public void set(String key, String value, int seconds) {
        SetArgs setArgs = new SetArgs();
        if (seconds > 0) {
            setArgs.ex(seconds);
        }
        redisCommands.set(key, value, setArgs);
    }

    public String get(String key) {
        return redisCommands.get(key);
    }


    public List<String> historyMessageList(String key){
        Range<String> range = Range.create("-","+");
        List<StreamMessage<String, String>> list = redisCommands.xrange(key,range);
        List<String> messageList = new ArrayList<>();
        for (StreamMessage<String, String> stringStringStreamMessage : list) {
            String msg = stringStringStreamMessage.getBody().get("msg");
            messageList.add(msg);
        }
        return messageList;
    }

    public void sendMsgToGroup(String key,String msg){
        Map<String,String> map = new HashMap<>();
        map.put("msg",msg);
        XAddArgs xAddArgs = new XAddArgs();
        xAddArgs.maxlen(20);
        redisCommands.xadd(key,xAddArgs,map);
    }

    public void createGroup(String groupName){
        redisCommands.zadd("group",System.currentTimeMillis(),groupName);
    }

    public List<String> getAllGroup(){
        return redisCommands.zrange("group", 0, -1);
    }


}
