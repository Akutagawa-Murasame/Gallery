package com.mura.gallery.mapper;

import com.mura.gallery.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akutagawa Murasame
 * 用户查询映射
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名称查询（用户名称唯一）
     * @param username username
     * @return User对象
     */
    User selectByUsername(String username);

    /**
     * 插入一条数据
     * @param user 要插入的user
     * @return 返回1插入成功，返回0插入失败
     */
    int insertOne(User user);
}
