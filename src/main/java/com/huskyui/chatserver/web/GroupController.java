package com.huskyui.chatserver.web;

import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.service.GroupService;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    @GetMapping("/list")
    public Result create(){
        return groupService.getAllGroup();
    }


}
