package com.mura.gallery.util;

import com.mura.gallery.exception.NotTheFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

/**
 * @author Akutagawa Murasame
 */
public class ImageUtil {
    /**
     * 防止属性被继承修改影响判断
     */
    private static final String AVATAR = "avatar";
    private static final String IMAGES = "images";

    /**
     * 根据上传的图片文件夹名称，选择将文件上传到哪里
     * @param file 图片，包含图片文件的所有属性和内容
     * @param parentDirectoryName 图片的父文件夹名，可能是用户头像avatar或用户上传图片gallery
     * @return 存储的文件路径，上传失败则为null
     */
    public static String upload(MultipartFile file, String parentDirectoryName) throws IOException {
        String fileName = file.getOriginalFilename();

        if ((null == fileName) || ("".equals(fileName))) {
            return null;
        }

        if (!AVATAR.equals(parentDirectoryName) && !IMAGES.equals(parentDirectoryName)) {
            return null;
        }

//        如果上传的文件不是用户头像文件，就视作用户上传的普通图片文件
//        用户头像不用分天数放置
        if (!AVATAR.equals(parentDirectoryName)) {
            //        普通的文件分天数存放
            LocalDate localDate = LocalDate.now();
            parentDirectoryName += (File.separator + localDate);
        }

//        存储的时候用的是随机名称
        try {
            fileName = FileUtil.makeRandomFileName(fileName);
        } catch (NotTheFileException e) {
            e.printStackTrace();

            return null;
        }

//        如果文件两次都没有创建成功，就返回null，文件存在也会返回null
        return (FileUtil.makeDirectoryOrFile(file, fileName, parentDirectoryName) || FileUtil.makeDirectoryOrFile(file, fileName, parentDirectoryName)) ? (parentDirectoryName + File.separator + fileName) : null;
    }
}