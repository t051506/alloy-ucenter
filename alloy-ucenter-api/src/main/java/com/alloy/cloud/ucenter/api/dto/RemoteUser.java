package com.alloy.cloud.ucenter.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: tankechao
 * @Date: 2020/10/7 14:04
 */
@Getter
@Setter
public class RemoteUser implements Serializable {
    private static final long serialVersionUID = 1051494517734199370L;

    private String username;//用户名
    private String password;
    private String personName;//姓名
    private String phone;
    private String idCard;
    private Integer gender;
    private Integer age;
    private Integer orgCode;//组织
    private Integer isLock;//0-正常，9-锁定
    private Integer isDelete;//0-正常，1-删除
}
