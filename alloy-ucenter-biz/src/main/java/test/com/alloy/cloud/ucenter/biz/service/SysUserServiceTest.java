package test.com.alloy.cloud.ucenter.biz.service;

import com.alloy.cloud.common.core.constant.CommonConstants;
import com.alloy.cloud.ucenter.biz.UcenterApplication;
import com.alloy.cloud.ucenter.biz.entity.SysUser;
import com.alloy.cloud.ucenter.biz.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
* SysUserService Tester.
*
* @author <Authors name>
* @since <pre>ʮ�� 8, 2020</pre>
* @version 1.0
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UcenterApplication.class)
@Slf4j
public class SysUserServiceTest {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Resource
    private SysUserMapper sysUserMapper;

@Before
public void before() throws Exception {
}

@After
public void after() throws Exception {
}

/**
*
* Method: insert(SysUser sysUser)
*
*/
@Test
public void testInsert() throws Exception {
    SysUser sysUser = new SysUser();
    sysUser.setUsername("admin");
    sysUser.setPassword("123456");
    sysUser.setUserId(Long.getLong(UUID.randomUUID().toString()));
    sysUser.setCreateTime(LocalDateTime.now());
    sysUser.setIsDelete(CommonConstants.STATUS_NORMAL);
    sysUser.setPassword(ENCODER.encode(sysUser.getPassword()));
    sysUser.setCreateBy("");
    sysUserMapper.insert(sysUser);
}

}
