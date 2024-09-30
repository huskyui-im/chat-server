package com.huskyui.chatserver.utils;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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


}
