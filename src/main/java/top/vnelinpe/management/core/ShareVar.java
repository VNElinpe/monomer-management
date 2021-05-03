package top.vnelinpe.management.core;

/**
 * 常量类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/19 9:50
 */
public class ShareVar {
    // 登录接口名
    public static final String LOGIN_URL="/user/login";
    // 退出登录接口名
    public static final String LOGOUT_URL="/user/logout";
    // 占位字体串
    public static final String PLACE_HOLDER_REG="\\{\\{.*?\\}\\}";
    // token过期时间的key
    public static final String TOKEN_EXPIRE_TIME_KEY = "t:e:t:k";
    // userKey, 记录当前登录用户uuid的key
    public static final String USER_TOKEN_KEY = "u:k";
    // userTokenKey
    public static final String USER_TOKEN_VALUE = "u:v";
    // 用户的uuid黑名单
    public static final String USER_KEY_BLACK = "u:b";
    // request中携带token的key
    public static final String REQUEST_TOKEN_KEY = "token";
    // userlock
    public static final String USER_LOCK_KEY = "u:l";
    // 验证码的key前缀
    public static final String VERIFY_CODE_KEY = "v:c";
    // 注册时验证码的消息场景码
    public static final String REGISTER_VERIFY_CODE_SCENE_CODE = "REGISTER_VERIFY_CODE";
    // 接口处理时间下限
    public static final long HANDLE_TIME = 10;
    // 登录开始时间
    public static final String LOGIN_START_TIME="loginStartTime";
    // 注销开始时间
    public static final String LOGOUT_START_TIME="logoutStartTime";
    // userDetails
    public static final String USER_DETAILS="userDetails";

    private ShareVar() {
    }
}
