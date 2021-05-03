package top.vnelinpe.management.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/20 8:35
 */
@Getter
@AllArgsConstructor
public enum OperationType {
    // 登录
    LOGIN(0, "登录"),
    // 注销
    LOGOUT(1, "注销"),
    // 添加用户
    ADD_USER(2, "添加用户"),
    // 修改用户信息
    MODIFY_USER(3, "修改用户信息"),
    // 授予用户角色权限信息
    GRANT_USER(4, "授予用户角色权限信息"),
    // 收回用户角色权限信息
    REVOKE_USER(5, "收回用户角色权限信息"),
    // 删除用户
    DELETE_USER(6, "删除用户"),
    // 添加角色
    ADD_ROLE(7, "添加角色"),
    // 添加角色权限
    GRANT_ROLE(8, "添加角色权限"),
    // 收回角色权限
    REVOKE_ROLE(9, "收回角色权限"),
    // 修改角色信息
    MODIFY_ROLE(10, "修改角色信息"),
    // 删除角色
    DELETE_ROLE(11, "删除角色"),
    // 添加权限
    ADD_AUTHORITY(12, "添加权限"),
    // 修改权限信息
    MODIFY_AUTHORITY(13, "修改权限信息"),
    // 删除权限
    DELETE_AUTHORITY(14, "删除权限"),

    ;

    private final int code;
    private final String desc;
}
