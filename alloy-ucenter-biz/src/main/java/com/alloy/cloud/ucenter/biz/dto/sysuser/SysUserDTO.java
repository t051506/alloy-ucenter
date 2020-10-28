package com.alloy.cloud.ucenter.biz.dto.sysuser;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 * @author tankechao
 * @since 2020-10-07 13:26:07
 */
@Data
public class SysUserDTO implements Serializable{
    private static final long serialVersionUID = 690001571587942544L;

    public interface AddValidGroup{}

    private Long userId;//主键ID
    private String username;//用户名
    private String password;
    private String salt;//随机盐
    private String phone;//简介
    private String headImg;//头像
    private Integer orgCode;//部门ID
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;//修改时间
    private Integer isLock;//0-正常，9-锁定
    private Integer isDelete;//0-正常，1-删除
}
