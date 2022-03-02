package com.mura.gallery.mapper;

import com.mura.gallery.entity.Image;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Akutagawa Murasame
 * 上传的文件信息映射
 */
@Mapper
public interface UploadImageMapper {
    /**
     * 插入一条图片数据
     * @return 成功返回1，失败返回0
     */
    int insertOne(Image image);
}