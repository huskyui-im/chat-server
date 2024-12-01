package com.huskyui.chatserver.mapper;

import com.huskyui.chatserver.domain.GroupBaseInfo;

import java.util.List;

/**
* @author huskyui
* @description 针对表【group_base_info】的数据库操作Mapper
* @createDate 2024-12-01 12:41:50
* @Entity com.huskyui.chatserver.domain.GroupBaseInfo
*/
public interface GroupBaseInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GroupBaseInfo record);

    int insertSelective(GroupBaseInfo record);

    GroupBaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupBaseInfo record);

    int updateByPrimaryKey(GroupBaseInfo record);

    List<GroupBaseInfo> selectList();

}
