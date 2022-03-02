package com.mura.gallery.service;

import com.mura.gallery.entity.Image;

/**
 * @author Akutagawa Murasame
 * 对于UploadImage的操作
 */
public interface UploadImageService {
    /**
     * 插入一条图片数据
     * @return 成功返回1，失败返回0
     */
    int insertOne(Image image);
}
