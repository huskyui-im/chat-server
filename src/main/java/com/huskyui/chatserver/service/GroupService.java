package com.huskyui.chatserver.service;

import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.utils.RemoteCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class GroupService {
    @Resource
    private RemoteCacheUtils remoteCacheUtils;

    public Result getAllGroup() {
        List<String> allGroup = remoteCacheUtils.getAllGroup();
        return Result.ok(allGroup);
    }



}
