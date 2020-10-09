package com.alloy.cloud.ucenter.biz;

import com.alloy.cloud.common.security.annotation.EnableCloudFeignClients;
import com.alloy.cloud.common.security.annotation.EnableCloudResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: tankechao
 * @Date: 2020/10/7 14:58
 */
@SpringCloudApplication
@EnableCloudResourceServer
@EnableCloudFeignClients
@ComponentScan("com.alloy.cloud")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
