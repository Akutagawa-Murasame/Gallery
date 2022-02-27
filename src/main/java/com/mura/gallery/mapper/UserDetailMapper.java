package com.mura.gallery.mapper;

import com.mura.gallery.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akutagawa Murasame
 * 用户详细信息查询映射
 */
@Mapper
public interface UserDetailMapper {
    /**
     * 根据Id查询用户详细信息
     * @param userId userId
     * @return 用户详细信息对象
     */
    UserDetail selectById(Integer userId);

    /**
     * 至少插入一列，一个字段（至少插入用户头像）
     * @param userDetail 用户详细信息对象
     * @return 返回1则成功，返回0则失败
     */
    int insertAtLeastOneColumn(UserDetail userDetail);
}
