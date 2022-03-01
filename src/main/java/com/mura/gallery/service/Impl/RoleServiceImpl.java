package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.Role;
import com.mura.gallery.mapper.RoleMapper;
import com.mura.gallery.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Akutagawa Murasame
 * 对于Role的具体操作
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource(type = RoleMapper.class)
    RoleMapper roleMapper;

    /**
     * 根据Id查询角色
     * @param userId userId
     * @return 角色信息
     */
    @Override
    public Role selectByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }

        return roleMapper.selectByUserId(userId);
    }

    /**
     * 插入一个角色
     * @param userId userId
     * @param roleName roleName
     * @return 返回1插入成功，0插入失败
     */
    @Override
    public int insertOne(Integer userId, String roleName) {
        if ((roleName == null) || ("".equals(roleName))) {
            return 0;
        }

        return roleMapper.insertOne(userId, roleName);
    }
}
