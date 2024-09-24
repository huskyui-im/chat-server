package com.huskyui.chatserver.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @RequestMapping("/login")
    public String login(){
        return "hello";
    }

}
