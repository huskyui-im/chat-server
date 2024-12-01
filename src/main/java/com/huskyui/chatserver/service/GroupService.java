package com.huskyui.chatserver.service;

import com.huskyui.chatserver.domain.ChatUser;
import com.huskyui.chatserver.domain.GroupBaseInfo;
import com.huskyui.chatserver.mapper.ChatUserMapper;
import com.huskyui.chatserver.mapper.GroupBaseInfoMapper;
import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.utils.RemoteCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GroupService {
    @Resource
    private ChatUserMapper chatUserMapper;

    @Resource
    private RemoteCacheUtils remoteCacheUtils;

    @Resource
    private GroupBaseInfoMapper groupBaseInfoMapper;

    public Result getAllGroup() {
        List<GroupBaseInfo> groupBaseInfos = groupBaseInfoMapper.selectList();
        return Result.ok(groupBaseInfos);
    }


    public void createGroup(String userId, String group, Object avatar) {
        ChatUser chatUser = chatUserMapper.selectByUsername(userId);
        if (chatUser != null){
            GroupBaseInfo groupBaseInfo = new GroupBaseInfo();
            groupBaseInfo.setAvatar((String)avatar);
            groupBaseInfo.setName(group);
            groupBaseInfo.setAdminId(chatUser.getId());
            groupBaseInfo.setRowStatus(1);
            groupBaseInfoMapper.insert(groupBaseInfo);
        }

    }
}
