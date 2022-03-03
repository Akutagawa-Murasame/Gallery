package com.mura.gallery.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Akutagawa Murasame
 * 对于文件上传太大的情况，拦截异常，并响应给用户
 */
@Slf4j
@ControllerAdvice
public class FileMaxSizeExceptionHandler {
    /**
     * 由于在进入控制器前就触发了这个异常，所以需要使用@ExceptionHandler来捕获这个异常
     */
    @ExceptionHandler({MaxUploadSizeExceededException.class, SizeLimitExceededException.class})
    public String fileSizeExceededHandler(RedirectAttributes attributes) {
        log.info("出现了文件上传异常");

//        attr.addAttribute:
//        这种方式就相当于重定向之后，在url后面拼接参数，这样在重定向之后的页面或者控制器再去获取url后面的参数就可以了，但这个方式因为是在url后面添加参数的方式，所以暴露了参数，有风险
//        attr.addFlashAttribute:
//        这种方式也能达到重新向带参，而且能隐藏参数，其原理就是放到session中，session在跳到页面后马上移除对象。
//————————————————
//        原文链接：https://blog.csdn.net/gd911202/article/details/63683841
        attributes.addFlashAttribute("message", "sum of file size should smaller than 8mb");

//        不能用转发，因为原请求无效
        return "redirect:/message";
    }
}