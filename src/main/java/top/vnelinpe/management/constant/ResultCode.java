package top.vnelinpe.management.constant;

/**
 * 返回的code和msg枚举类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 10:29
 */
public enum ResultCode {
    // 通用操作失败
    FAIL(-1, "操作失败"),
    // 通用操作成功
    SUCCESS(0, "操作成功"),
    // 请求方式错误
    BAD_RQUEST_METHOD(1, "请求方式错误"),
    // 请求参数错误
    BAD_REQUEST_ARGS(2, "请求参数错误"),
    // 缺少权限
    LEAK_AUTHORITY(3, "没有相关权限"),
    // 不允许匿名访问
    DENY_ANNO(4, "不允许匿名访问，请登录"),
    // token过期
    TOKEN_OUT_OF_TIME(5, "token过期"),
    // token解析错误
    TOKEN_PARSE_FAIL(6, "token解析错误"),
    // 退出失败
    LOGOUT_FAIL(7, "退出登录失败"),
    // 用户名或密码错误
    BAD_USER_OR_PASSWORD(8, "用户名或密码错误"),
    // 生成token失败
    GENERATE_TOKEN_FAIL(9, "生成token失败"),
    // 获取类加载信息失败
    GET_CLASS_LOADING_FAIL(10, "获取类加载信息失败"),
    // 获取编译信息失败
    GET_COMPILATION_FAIL(11, "获取编译信息失败"),
    // 获取内存使用信息失败
    GET_MEMORY_USAGE_FAIL(12, "获取内存使用信息失败"),
    // 获取操作系统信息失败
    GET_OS_FAIL(13, "获取操作系统信息失败"),
    // 获取运行时信息失败
    GET_RUNTIME_FAIL(14, "获取运行时信息失败"),
    // 获取线程信息失败
    GET_THREAD_FAIL(15, "获取线程信息失败"),
    // 分页查询日志失败
    LIST_LOG_FAIL(16, "分页查询日志失败"),
    // 导出日志失败
    DUMP_LOG_FAIL(17, "导出日志失败"),
    // 分页查询用户失败
    LIST_USER_FAIL(18, "分页查询用户失败"),
    // 查询用户详情
    FIND_USER_FAIL(19, "查询用户详情失败"),
    // 查询角色失败
    FIND_ROLE_FAIL(20, "查询角色详情失败"),
    // 查询权限详情失败
    FIND_AUTHORITY_FAIL(21, "查询权限详情失败"),
    // 删除用户失败
    DELETE_USER_FAIL(22, "删除用户失败"),
    // 删除角色失败
    DELETE_ROLE_FAIL(23, "删除角色失败"),
    // 删除权限失败
    DELETE_AUTHORITY_FAIL(24, "删除权限失败"),
    // 修改用户失败
    MODIFY_USER_FAIL(25, "删除用户失败"),
    // 修改角色失败
    MODIFY_ROLE_FAIL(26, "删除角色失败"),
    // 修改权限失败
    MODIFY_AUTHORITY_FAIL(27, "删除权限失败"),
    // 添加用户失败
    ADD_USER_FAIL(28, "添加用户失败"),
    // 添加权限失败
    ADD_AUTHORITY_FAIL(29, "添加权限失败"),
    // 用户添加角色失败
    GRANT_ROLE_FAIL(30, "用户添加角色失败"),
    // 生成验证码失败
    GENERATE_CODE_FAIL(31, "生成验证码失败"),
    // 验证码超时
    VERIFY_CODE_TIME_OUT(32, "验证码超时"),
    // 验证码错误
    VERIFY_CODE_ERROR(33, "验证码错误"),
    // 邮箱已存在
    MAIL_DUPLICATED(34, "邮箱已存在"),
    // 手机号已存在
    PHONE_DUPLICATED(35, "手机号已存在"),
    // 用户名已存在
    USERNAME_DUPLICATED(36, "用户名已存在"),
    // 昵称已存在
    SURNAME_DUPLICATED(37, "昵称已存在"),


    ;

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
