package com.huskyui.chatserver.service;

import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.utils.RemoteCacheUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class AuthService {

    @Resource
    private RemoteCacheUtils remoteCacheUtils;


    public Result login(String username) {
        String token = UUID.randomUUID().toString();
        remoteCacheUtils.set(token,username,60*30);
        return Result.ok(token);
    }


}
