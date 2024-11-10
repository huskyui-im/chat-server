package com.huskyui.chatserver.mapper;

import com.huskyui.chatserver.domain.ChatUser;
import org.apache.ibatis.annotations.Param;

/**
* @author huskyui
* @description 针对表【chat_user】的数据库操作Mapper
* @createDate 2024-11-09 19:36:45
* @Entity com.huskyui.chatserver.domain.ChatUser
*/
public interface ChatUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ChatUser record);

    int insertSelective(ChatUser record);

    ChatUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatUser record);

    int updateByPrimaryKey(ChatUser record);

    ChatUser selectByUsername(@Param("username") String username);


    ChatUser selectByUsernameAndPassword(@Param("username") String username,@Param("password") String encrypt);
}
