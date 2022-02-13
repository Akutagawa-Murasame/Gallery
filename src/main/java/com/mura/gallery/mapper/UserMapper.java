package com.mura.gallery.mapper;

import com.mura.gallery.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akutagawa Murasame
 */
@Mapper
public interface UserMapper {
    User selectByUsername(String username);

    int insertOne(User user);
}
