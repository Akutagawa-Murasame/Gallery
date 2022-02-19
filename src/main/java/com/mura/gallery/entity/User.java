package com.mura.gallery.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Akutagawa Murasame
// * 用户信息，有时候用户会被暂时禁用（enabled=false）
 */
@Data
public class User implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private List<Role> roles;
    private Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        实际上，这个项目的一个user只对应一个role
        return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("ROLE_" + roles.get(0).getRoleName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 账户是否过期
     * 当前没有账户过期的逻辑，所以暂时返回永远为true
     * 后续可能通过spring boot的@Scheduled注解实现自动清理账户功能
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否上锁
     * 当前没有账户上锁的逻辑，所以暂时返回永远为true
     * 一般通过enabled属性完成这个逻辑
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     * 密码和账户同生共死
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
