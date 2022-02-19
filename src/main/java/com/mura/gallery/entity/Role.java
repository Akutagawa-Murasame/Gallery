package com.mura.gallery.entity;

import lombok.Data;

/**
 * @author Akutagawa Murasame
 * 用户角色,分为拉黑、会员、超级会员
 */
@Data
public class Role {
    private String userId;
    private String roleName;
}
