package com.alloy.cloud.ucenter.api.feign.fallback;

import com.alloy.cloud.common.core.base.R;
import com.alloy.cloud.ucenter.api.dto.RemoteUser;
import com.alloy.cloud.ucenter.api.feign.RemoteUserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteUserServiceFallbackImpl implements RemoteUserService {

	@Setter
	private Throwable cause;

	@Override
	public R<RemoteUser> loadByUsername(String from, String username) {
		log.error("loadByUsername:{}, error:{}",username,cause.getMessage(),cause);
		return R.failed(cause.getMessage());
	}
}
