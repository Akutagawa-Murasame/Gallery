package com.mura.gallery.service;

import com.mura.gallery.entity.Role;

import java.util.List;

/**
 * @author Akutagawa Murasame
 * 对于Role的操作
 */
public interface RoleService {
    /**
     * 根据Id查询角色
     * @param userId userId
     * @return 用户角色
     */
    Role selectByUserId(Integer userId);

    /**
     * 插入一个角色
     * @param userId userId
     * @param roleName roleName
     * @return 返回1插入成功，0插入失败
     */
    int insertOne(Integer userId, String roleName);
}
