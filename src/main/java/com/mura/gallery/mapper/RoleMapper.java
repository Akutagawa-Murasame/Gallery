package com.mura.gallery.mapper;

import com.mura.gallery.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Akutagawa Murasame
 * 角色查询映射
 */
@Mapper
public interface RoleMapper {
    /**
     * 根据Id查询角色
     * @param userId userId
     * @return 角色链表
     */
    Role selectByUserId(Integer userId);

    /**
     * 插入一个角色
     * @param userId userId
     * @param roleName roleName
     * @return 返回1插入成功，0插入失败
     */
    int insertOne(@Param("userId") Integer userId, @Param("roleName") String roleName);
}
