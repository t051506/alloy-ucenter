package com.alloy.cloud.ucenter.biz.config;

import com.alloy.cloud.common.core.util.SpringContextUtils;
import com.alloy.cloud.ucenter.biz.constants.UcenterCache;
import com.alloy.cloud.ucenter.biz.snowflake.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: tankechao
 * @Date: 2019/12/5 14:35
 */
@Slf4j
@Component
public class SnowflakeConfig {

    @Value("${spring.application.name}")
    private String serverName;

    private static volatile SnowflakeIdWorker instance;

    public SnowflakeIdWorker getInstance() {
        if (instance == null) {
            synchronized (SnowflakeIdWorker.class) {
                if (instance == null) {
                    StringRedisTemplate stringRedisTemplate = SpringContextUtils.getBean(StringRedisTemplate.class);
                    Long workerId = stringRedisTemplate.opsForValue().increment(UcenterCache.PROJECT_CACHE + "workerId:" + serverName, 1); //不存在则返回1，存在则+1并返回
                    Long dataCenterId = stringRedisTemplate.opsForValue().increment(UcenterCache.PROJECT_CACHE + "dataCenterId:" + serverName, 1);
                    instance = new SnowflakeIdWorker(workerId % 32, dataCenterId % 32);
                }
            }
        }
        return instance;
    }
}
