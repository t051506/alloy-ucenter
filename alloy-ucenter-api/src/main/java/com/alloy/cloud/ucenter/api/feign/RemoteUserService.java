package com.alloy.cloud.ucenter.api.feign;

import com.alloy.cloud.common.core.base.R;
import com.alloy.cloud.common.core.constant.ServiceNameConstants;
import com.alloy.cloud.ucenter.api.dto.RemoteUser;
import com.alloy.cloud.ucenter.api.feign.factory.RemoteUserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Author: tankechao
 * @Date: 2020/10/7 14:06
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.UMPS_SERVICE,
        fallbackFactory = RemoteUserServiceFallbackFactory.class)
public interface RemoteUserService {

    @GetMapping("/provider/v1/user/info/{username}")
    R<RemoteUser> loadByUsername(@RequestHeader("form") String from, @PathVariable("username") String username);
}
