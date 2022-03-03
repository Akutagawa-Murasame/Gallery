package com.mura.gallery.util;

import com.mura.gallery.exception.NotTheFileException;
import com.mura.gallery.exception.NotThePictureException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @author Akutagawa Murasame
 */
public class FileUtil {
    private static final String DOT_JPG = ".jpg";
    private static final String DOT_PNG = ".png";

    /**
     * 根据上传的文件或文件夹名称，创建文件
     * @param fileOrDirectoryName 文件名称
     * @param parentDirectoryName 文件要被保存的父文件夹名称
     * @return 是否创建成功
     */
    public static boolean makeDirectoryOrFile(MultipartFile fileStream, String fileOrDirectoryName, String parentDirectoryName) throws IOException {
        File file = new File(parentDirectoryName, fileOrDirectoryName);

//        文件不存在就创建文件，否则根据文件上传是否成功返回
        if (!file.exists()) {
//            Java File类 mkdir 不能创建多层目录，如果是多层，可以调mkdirs
            new File(parentDirectoryName).mkdirs();

            fileStream.transferTo(new File(parentDirectoryName + File.separator + fileOrDirectoryName).getAbsoluteFile());

            return true;
        }

        return false;
    }

    /**
     * 给文件随机命名
     * @param originFileName 带有文件后缀名的文件名称
     * @return 文件随机后的名称
     */
    public static String makeRandomFileName(String originFileName) {
//        '.'后的字符串就是文件后缀
        int suffixIndex = originFileName.lastIndexOf(".");

        if (suffixIndex == -1) {
            throw new NotTheFileException("not a file");
        }

        String suffix = originFileName.substring(suffixIndex);
        String newFileName = UUID.randomUUID().toString();

        if (DOT_JPG.equals(suffix) || DOT_PNG.equals(suffix)) {
            newFileName += suffix;
        } else {
            throw new NotThePictureException("not a picture");
        }

        return newFileName;
    }
}
