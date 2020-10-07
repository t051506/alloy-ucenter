package com.alloy.cloud.ucenter.biz.controller.provider.v1;

import com.alloy.cloud.common.core.base.R;
import com.alloy.cloud.ucenter.api.dto.RemoteUser;
import com.alloy.cloud.ucenter.biz.entity.SysUser;
import com.alloy.cloud.ucenter.biz.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: tankechao
 * @Date: 2020/10/7 14:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/provider/v1/user")
public class SysUserProviderController {

    private final SysUserService sysUserService;

    @GetMapping("/info/{username}")
    public R<RemoteUser> loadByUserName(@RequestHeader("form") String from,@PathVariable("username") String username){
        RemoteUser remoteUser = new RemoteUser();
        SysUser sysUser = sysUserService.queryByUserName(username);
        BeanUtils.copyProperties(sysUser,remoteUser);
        return R.ok(remoteUser);
    }
}
