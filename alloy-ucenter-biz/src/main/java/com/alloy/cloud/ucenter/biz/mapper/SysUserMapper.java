package com.alloy.cloud.ucenter.biz.mapper;

import com.alloy.cloud.ucenter.biz.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表
 * @author tankechao
 * @since 2020-10-07 13:26:07
 */
@Mapper
public interface SysUserMapper {

    void insert(SysUser sysUser);

    SysUser queryByUserName(@Param("username") String username);
}
