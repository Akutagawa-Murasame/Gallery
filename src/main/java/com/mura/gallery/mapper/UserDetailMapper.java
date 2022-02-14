package com.mura.gallery.mapper;

import com.mura.gallery.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akutagawa Murasame
 */
@Mapper
public interface UserDetailMapper {
    UserDetail selectById(Integer userId);
}
