package com.huskyui.chatserver.service;

import com.huskyui.chatserver.domain.ChatUser;
import com.huskyui.chatserver.exception.CommonException;
import com.huskyui.chatserver.mapper.ChatUserMapper;
import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.model.auth.RegisterRequest;
import com.huskyui.chatserver.utils.AESUtils;
import com.huskyui.chatserver.utils.RemoteCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    @Resource
    private RemoteCacheUtils remoteCacheUtils;

    @Resource
    private ChatUserMapper chatUserMapper;

    private static final String secretKey = "m5XaUcaYBKAEFJgq";


    public Result login(String username) {
        String token = UUID.randomUUID().toString();
        remoteCacheUtils.set(token,username,60*30);
        return Result.ok(token);
    }


    public Result register(RegisterRequest request) {
        try {
            checkRegisterParams(request);
            checkUserDuplicate(request);
            registerUserToDb(request);
            return Result.ok();
        }catch (CommonException e){
            return Result.fail(e.getCode(),e.getMsg());
        }
    }

    private void registerUserToDb(RegisterRequest request) {
        try {
            ChatUser chatUser = new ChatUser();
            chatUser.setCreateUser("用户");
            chatUser.setUsername(request.getUsername());
            String encrypt = AESUtils.encrypt(request.getPassword(), secretKey);
            chatUser.setEncryptPassword(encrypt);
            chatUser.setAvatar(request.getAvatar());
            chatUserMapper.insertSelective(chatUser);
        }catch (Exception e){
            log.error("create user error :{}",e);
            throw new CommonException(3001,"创建失败");
        }


    }

    private void checkUserDuplicate(RegisterRequest request) {
        ChatUser chatUser = chatUserMapper.selectByUsername(request.getUsername());
        if (chatUser != null){
            throw new CommonException(2001,"用户已存在");
        }
    }

    private void checkRegisterParams(RegisterRequest request) {
        if (StringUtils.isEmpty(request.getUsername())) {
            throw new CommonException(1001,"用户名没传");
        }
        if (StringUtils.isEmpty(request.getPassword())){
            throw new CommonException(1002,"密码未传");
        }
        if (StringUtils.isEmpty(request.getAvatar())){
            throw new CommonException(1003,"头像未传");
        }

    }
}
