package com.huskyui.chatserver.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public RedisCommands<String,String> getRedisCommands(){
//        RedisClient client = RedisClient.create("redis://localhost:6379");
//        StatefulRedisConnection<String, String> connection = client.connect();
//        return connection.sync();
//    }
//
//}
