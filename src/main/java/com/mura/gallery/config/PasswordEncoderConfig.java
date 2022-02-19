package com.mura.gallery.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Akutagawa Murasame
 * BCryptPasswordEncoder，非对称加密，由加密后的密文（存在数据库中）无法反向解密为原密码
 * 将网页输入的密码采用此加密器加密成密文，与数据库查询得到的密文相比较，判断密码是否正确
 */
@Configuration
public class PasswordEncoderConfig {
    /**
     * 启用了Spring boot的@Configuration的beanProxyMethod（即默认）
     * 如果想要更快速启动服务器可将该属性设置为false，但是容器中可能出现多
     * 个不必要PasswordEncoder
     * @return BCryptPasswordEncoder（后续可能改为通过配置文件自由配
     * 置密码加密器）（当前只支持非对称加密，如果想要支持对称加密，可能要更
     * 改UserDetailsServiceImpl的验证逻辑）
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
