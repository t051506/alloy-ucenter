package com.alloy.cloud.ucenter.biz.constants;

/**
 * @Author: tankechao
 * @Date: 2020/10/17 15:03
 */
public class UcenterCache {

    public static final String BASE = "paas:";

    /** 小项目名称 */
    public static final String PROJECT = "ucenter:";

    /** 小项目缓存 */
    public static final String PROJECT_CACHE = BASE + PROJECT + "cache:";

    /** 全局缓存 */
    public static final String GLOBAL_CACHE = BASE + "cache:";

    /** 登录用户 */
    public static final String USER_CACHE = BASE + "cache:" + "loginInfo:";

    /** 基础信息 */
    public static final String BASE_INFO_CACHE = BASE + "cache:" + "baseInfo:";

    /** 项目锁 */
    public static final String PAT_LOCK = BASE + PROJECT + "lock:";

    /** 全局锁 */
    public static final String GLOBAL_LOCK = BASE + "lock:";


}
