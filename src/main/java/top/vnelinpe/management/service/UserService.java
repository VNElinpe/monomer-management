package top.vnelinpe.management.service;

import top.vnelinpe.management.dto.sys.*;
import top.vnelinpe.management.query.sys.StringQuery;
import top.vnelinpe.management.vo.sys.SysUserAddVO;
import top.vnelinpe.management.query.sys.SysUserQuery;
import top.vnelinpe.management.vo.sys.SysUserUpdateRoleVO;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.SysUserVO;
import top.vnelinpe.management.vo.sys.SysUserDetailVO;
import top.vnelinpe.management.model.sys.SysUserDO;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/4/22 14:33
 */
public interface UserService {
    /**
     * 通过id查询详细用户信息
     * @param userId
     * @return
     */
    SysUserDetailsDTO getUserById(Long userId);

    /**
     * 查询用户信息列表, 支持全部或分页
     * @param sysUserQuery
     * @return
     */
    PageOutDTO<SysUserDTO> list(SysUserQuery sysUserQuery);

    /**
     * 按编号删除用户
     * @param userId
     */
    void deleteById(Long userId);

    /**
     * 修改用户信息
     * @param sysUserUpdateDTO
     */
    void modify(SysUserUpdateDTO sysUserUpdateDTO);

    /**
     * 授予用户角色权限
     * @param sysUserUpdateRoleDTO
     */
    void grant(SysUserUpdateRoleDTO sysUserUpdateRoleDTO);

    /**
     * 收回用户角色权限
     * @param sysUserUpdateRoleDTO
     */
    void revoke(SysUserUpdateRoleDTO sysUserUpdateRoleDTO);

    /**
     * 添加用户
     * @param sysUserAddDTO
     */
    void addUser(SysUserAddDTO sysUserAddDTO);

    /**
     * 按邮箱查询用户基本信息
     * @param email
     * @return
     */
    SysUserDTO findUserByEmail(String email);

    /**
     * 按用户名查询用户的信息, 包括权限信息
     * @param username
     * @return
     */
    UserDetailsDTO getUserByUsername(String username);

    /**
     * 按用户名查询用户基本信息
     * @param username
     * @return
     */
    SysUserDTO findUserByUsername(String username);

    /**
     * 按昵称查询用户基本信息
     * @param surname
     * @return
     */
    SysUserDTO findUserBySurname(String surname);

    /**
     * 按手机号码查询用户基本信息
     * @param phone
     * @return
     */
    SysUserDTO findUserByPhone(String phone);
}
