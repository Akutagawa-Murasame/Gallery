package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.Image;
import com.mura.gallery.mapper.UploadImageMapper;
import com.mura.gallery.service.UploadImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 我自己
 * 对于UploadImage的操作
 */
@Service
public class UploadImageServiceImpl implements UploadImageService {
    @Resource(type = UploadImageMapper.class)
    UploadImageMapper uploadImageMapper;

    @Override
    public int insertOne(Image image) {
        return uploadImageMapper.insertOne(image);
    }
}
