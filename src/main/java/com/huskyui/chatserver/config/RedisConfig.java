package com.huskyui.chatserver.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RedisConfig {
    @Value("${redis.url}")
    private String redisUrl;



    @Bean
    public RedisCommands<String,String> getRedisCommands(){
        log.info("init redis-cli");
        RedisClient client = RedisClient.create(redisUrl);
        StatefulRedisConnection<String, String> connection = client.connect();
        log.info("init redis-cli success");
        return connection.sync();
    }

}
