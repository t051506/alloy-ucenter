package com.alloy.cloud.ucenter.biz.service;

import com.alloy.cloud.ucenter.biz.config.SnowflakeConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author: tankechao
 * @Date: 2019/12/5 15:02
 */
@Service
@AllArgsConstructor
public class IdGenService {

    private final SnowflakeConfig snowflakeConfig;

    public long genId() {
        return snowflakeConfig.getInstance().nextId();
    }
}
