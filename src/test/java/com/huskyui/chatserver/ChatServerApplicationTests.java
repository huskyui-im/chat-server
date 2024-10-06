package com.huskyui.chatserver;

import com.huskyui.chatserver.utils.RemoteCacheUtils;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ChatServerApplicationTests {

    @Resource
    private RemoteCacheUtils remoteCacheUtils;

    @Test
    public void testRedis(){
        List<String> objects = remoteCacheUtils.historyMessageList("mystream");
        System.out.println(objects);
    }

}
