package com.mura.gallery.mapper;

import com.mura.gallery.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Akutagawa Murasame
 */
@Mapper
public interface RoleMapper {
    List<Role> selectByUserId(Integer id);

    int insertOne(int userId, String roleName);
}
