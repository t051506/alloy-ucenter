package com.alloy.cloud.ucenter.biz.entity;

import com.alloy.cloud.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
/**
 * 用户
 *
 * @author tankechao
 * @since 2020-12-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity{
    private static final long serialVersionUID = -76423951816146867L;

    /**
     * 主键ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    private String password;

    /**
     * 姓名
     */
    private String personName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 部门ID
     */
    private Integer orgCode;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 性别(0:男,1:女)
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 0-正常，9-锁定
     */
    private Integer isLock;
}
