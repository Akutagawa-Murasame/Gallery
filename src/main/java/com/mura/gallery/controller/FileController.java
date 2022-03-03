package com.mura.gallery.controller;

import com.mura.gallery.entity.Image;
import com.mura.gallery.entity.User;
import com.mura.gallery.service.UploadImageService;
import com.mura.gallery.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * @author Akutagawa Murasame
 * 文件上传下载控制器
 */
@Controller
public class FileController {
    @Resource(type = UploadImageService.class)
    UploadImageService uploadImageService;

    /**
     * 将用户上传的图片按照日期保存在不同的目录下，并将图片的目录存在数据库
     *
     * @param request 包含多个文件的request
     * @return 重定向到提示信息视图，页面上显示文件上传结果
     */
    @PostMapping(value = "/uploadFiles", consumes = "multipart/*")
    public String uploadFilesFromHtml(MultipartHttpServletRequest multipartRequest,
                                      HttpServletRequest request) {
//        首先确认用户是否已经登录，即使拦截器已经拦截了没有登录的访问，但是仍然不能轻易相信别人的代码（指spring）
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            request.setAttribute("error", "please login in first");
            return "forward:/sign_in";
        } else {
//            移除上次添加的提示
            request.removeAttribute("error");
        }

//        MultiValueMap相当于是可以包含重复键的HashMap，因为图片名称可能相同，用来保存多个图片信息
//        MultipartFile是Spring的分段上传文件类
//        由于在配置文件中设置了文件上传的最大大小，因此不会收到太多图片
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();

//        upload-multi-file为html表单中定义的input[type="file"]的name属性
        List<MultipartFile> list = map.get("upload-multi-file");

//        存储文件路径的临时变量
        String tempFilePath;

//        存储每一个文件，记录成功的个数
        int count = 0;
        for (MultipartFile file : list) {
            try {
                tempFilePath = ImageUtil.upload(file, "images");

                if (null == tempFilePath) {
//                    文件表单提交时，会默认上传一个空文件，所以size为1，因此为了显示合理，这里不显示上传数量和总数
                    throw new IOException("inner server error");
                } else {
                    if (uploadImageService.insertOne(new Image(user.getId(), new Date(new java.util.Date().getTime()), tempFilePath)) != 0) {
                        ++count;
                    } else {
                        throw new IOException("server internal error, not your fault, success | sum : " + count + " | " + list.size());
                    }
                }
            } catch (IOException e) {
                request.setAttribute("message", e.getMessage());

                return "forward:/message";
            }
        }

//        终于上传完成了，返回提示信息
        request.setAttribute("message", "upload finished, success / sum : " + count + "/" + list.size());
        return "forward:/message";
    }
}
