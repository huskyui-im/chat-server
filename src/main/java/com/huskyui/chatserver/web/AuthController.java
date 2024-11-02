package com.huskyui.chatserver.web;

import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.model.auth.LoginRequest;
import com.huskyui.chatserver.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request){
        return authService.login(request.getUsername());
    }

}
