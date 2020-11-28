package com.alloy.cloud.ucenter.biz.entity;

import com.alloy.cloud.common.core.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表
 * @author tankechao
 * @since 2020-10-07 13:26:07
 */
@Getter
@Setter
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 363154537929627918L;

    private Long userId;//主键ID
    private String username;//用户名
    private String password;
    private String salt;//随机盐
    private String phone;//简介
    private String headImg;//头像
    private Integer orgCode;//组织编码
    private Integer isLock;//0-正常，9-锁定

}
