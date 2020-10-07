package com.alloy.cloud.ucenter.biz.controller.api.v1;


import com.alloy.cloud.common.core.base.R;
import com.alloy.cloud.ucenter.biz.dto.sysuser.SysUserDTO;
import com.alloy.cloud.ucenter.biz.entity.SysUser;
import com.alloy.cloud.ucenter.biz.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 * @author tankechao
 * @since 2020-10-07 13:26:07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@Api(tags = "用户管理")
public class SysUserController {

    private final SysUserService sysUserService;

    @PostMapping
    @ApiOperation("新增")
    public R create(@Validated(SysUserDTO.AddValidGroup.class) @RequestBody SysUserDTO dto){
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto,sysUser);
        sysUserService.insert(sysUser);
        return R.ok();
    }
}
