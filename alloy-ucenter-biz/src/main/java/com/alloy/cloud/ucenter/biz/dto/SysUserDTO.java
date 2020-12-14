package com.alloy.cloud.ucenter.biz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * 用户
 *
 * @author tankechao
 * @since 2020-12-12
 */
@Data
@ApiModel(description="用户")
public class SysUserDTO implements Serializable{
    private static final long serialVersionUID = -77393810705988113L;

    @ApiModelProperty(value = "主键ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    private String password;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "部门ID")
    private Integer orgCode;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @ApiModelProperty(value = "性别(0:男,1:女)")
    private Integer gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "0-正常，9-锁定")
    private Integer isLock;
}
