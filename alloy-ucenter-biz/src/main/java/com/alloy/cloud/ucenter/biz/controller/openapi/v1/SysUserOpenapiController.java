package com.alloy.cloud.ucenter.biz.controller.openapi.v1;

import cn.hutool.core.util.ObjectUtil;
import com.alloy.cloud.common.core.base.R;
import com.alloy.cloud.ucenter.biz.dto.SysUserDTO;
import com.alloy.cloud.ucenter.biz.entity.SysUser;
import com.alloy.cloud.ucenter.biz.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: tankechao
 * @Date: 2020/10/19 16:20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/openapi/v1/user")
public class SysUserOpenapiController {

    private final SysUserService sysUserService;

    @GetMapping("/{username}")
    public R<SysUserDTO> queryByUserName(@PathVariable("username") String username){
        SysUserDTO remoteUser = new SysUserDTO();
        SysUser sysUser = sysUserService.queryByUserName(username);
        if(ObjectUtil.isNull(sysUser)){
            return R.failed("用户不存在");
        }
        BeanUtils.copyProperties(sysUser,remoteUser);
        return R.ok(remoteUser);
    }
}
