package com.mura.gallery.config;

import com.mura.gallery.service.Impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

/**
 * @author Akutagawa Murasame
 * 登录验证设置，主页和登录页不需要登录就可以访问，静态资源不需要登录就可以访问
 */
@Configuration
public class LoginConfig extends WebSecurityConfigurerAdapter {
    @Resource(type = UserDetailsServiceImpl.class)
    UserDetailsService userDetailsService;

    /**
     * 这个函数是spring security用于设置密码校验机制的
     * @param auth 通过这个对象设置参数
     * @throws Exception 意想不到的错误
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 设置拦截逻辑
     * @param http 通过这个对象设置参数
     * @throws Exception 意想不到的错误
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置认证
        http.formLogin()
                // 哪个URL为登录页面
                .loginPage("/sign_in")
                // 当发现什么URL时执行登录逻辑
                .loginProcessingUrl("/login")
                // 成功后跳转到哪里
                .successForwardUrl("/index");

        // 设置URL的授权问题
        // 多个条件取交集
        http.authorizeRequests()
                // 匹配 / 控制器  permitAll() 不需要认证就可以访问，登录页不需要认证
                .antMatchers("/sign_in").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/submit_register").permitAll()
//                设置哪些路径需要权限
                .antMatchers("/**").hasAnyAuthority("member", "super")
                // anyRequest() 所有请求   authenticated() 登陆了才能访问
                .anyRequest().authenticated();

        // 关闭csrf
        http.csrf().disable();
    }

    /**
     * 设置放行逻辑
     * @param web 通过这个对象设置参数
     */
    @Override
    public void configure(WebSecurity web) {
//        跳过验证的资源，直接忽略
        web.ignoring().antMatchers("/static/**", "/", "/public/**");
    }
}
