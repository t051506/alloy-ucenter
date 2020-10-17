package com.alloy.cloud.ucenter.biz.service;

import com.alloy.cloud.common.core.constant.CommonConstants;
import com.alloy.cloud.common.security.util.SecurityUtils;
import com.alloy.cloud.ucenter.biz.entity.SysUser;
import com.alloy.cloud.ucenter.biz.mapper.SysUserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户表
 * @author tankechao
 * @since 2020-10-07 13:26:07
 */
@Service
@Slf4j
@AllArgsConstructor
public class SysUserService{

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final SysUserMapper sysUserMapper;

    private final IdGenService idGenService;

    @Transactional(rollbackFor = Exception.class)
    public void insert(SysUser sysUser) {
        sysUser.setUserId(idGenService.genId());
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setIsDelete(CommonConstants.STATUS_NORMAL);
        sysUser.setPassword(ENCODER.encode(sysUser.getPassword()));
        sysUser.setCreateBy(SecurityUtils.getUser().getUsername());
        sysUserMapper.insert(sysUser);
    }

    public SysUser queryByUserName(String username){
        return sysUserMapper.queryByUserName(username);
    }

}
