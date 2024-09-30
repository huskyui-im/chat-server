package com.huskyui.chatserver;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ChatServerApplicationTests {

    @Resource
    private RedisCommands<String,String> redisCommands;

    @Test
    public void testRedis(){
        SetArgs setArgs = new SetArgs();
        setArgs.ex(1000);
        redisCommands.set("key","value",setArgs);
        System.out.println(redisCommands.get("key"));
    }

}
