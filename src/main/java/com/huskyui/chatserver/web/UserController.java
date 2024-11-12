package com.huskyui.chatserver.web;

import com.huskyui.chatserver.annotation.RequestLog;
import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/info")
    @RequestLog
    public Result info(@RequestParam("userId")String userId){
        return userService.getUserInfoByUserId(userId);

    }

}
