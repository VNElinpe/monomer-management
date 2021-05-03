package top.vnelinpe.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.vnelinpe.management.dto.sys.*;
import top.vnelinpe.management.query.sys.LongQuery;
import top.vnelinpe.management.query.sys.StringQuery;
import top.vnelinpe.management.vo.sys.SysUserAddVO;
import top.vnelinpe.management.query.sys.SysUserQuery;
import top.vnelinpe.management.vo.sys.SysUserUpdateVO;
import top.vnelinpe.management.vo.sys.SysUserVO;
import top.vnelinpe.management.vo.sys.SysUserDetailVO;
import top.vnelinpe.management.model.sys.SysAuthorityDO;
import top.vnelinpe.management.model.sys.SysUserDO;

import java.util.List;
import java.util.Set;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 9:33
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名id查权限
     *
     * @param userId
     * @return
     */
    Set<SysAuthorityDO> getAuthorityByUserId(Long userId);

    /**
     * 根据id查询用户详细信息
     * @param userId
     * @return
     */
    SysUserDetailsDTO getUserById(Long userId);

    /**
     * 查询用户基本信息列表
     * @param sysUserQuery
     * @return
     */
    List<SysUserDTO> listUser(SysUserQuery sysUserQuery);

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
     * 添加用户角色
     * @param userId
     * @param roleList
     */
    void addRoles(@Param("userId") Long userId, @Param("roleList") List<Long> roleList);

    /**
     * 添加用户额外的权限
     * @param userId
     * @param authorityList
     */
    void addAuthorities(@Param("userId") Long userId, @Param("authorityList") List<Long> authorityList);

    /**
     * 收回用户的权限
     * @param userId
     * @param roleList
     */
    void removeRoles(@Param("userId") Long userId, @Param("roleList") List<Long> roleList);

    /**
     * 收回用户额外的权限
     * @param userId
     * @param authorityList
     */
    void removeAuthorities(@Param("userId") Long userId, @Param("authorityList") List<Long> authorityList);

    /**
     * 添加用户
     * @param sysUserAddDTO
     */
    void addUser(SysUserAddDTO sysUserAddDTO);

    /**
     * 按email查询用户基本信息
     * @param email
     * @return
     */
    SysUserDO findUserByEmail(String email);

    /**
     * 按昵称查询用户基本信息
     * @param surname
     * @return
     */
    SysUserDO findUserBySurname(String surname);

    /**
     * 按手机号码查询用户基本信息
     * @param phone
     * @return
     */
    SysUserDO findUserByPhone(String phone);

    /**
     * 按用户名查询用户详细信息
     * @param username
     * @return
     */
    UserDetailsDTO getUserByUsername(String username);

    /**
     * 查询用户的额外权限
     * @param userId
     * @return
     */
    Set<SysAuthorityDO> listExtraAuthorityByUserId(Long userId);

    /**
     * 按用户名查询用户基本信息
     * @param username
     * @return
     */
    SysUserDO findUserByUsername(String username);
}
