package top.vnelinpe.management.service;

import top.vnelinpe.management.dto.sys.*;
import top.vnelinpe.management.query.sys.SysRoleQuery;
import top.vnelinpe.management.vo.sys.SysRoleUpdateAuthoritiesVO;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.SysRoleVO;
import top.vnelinpe.management.vo.sys.SysRoleDetailVO;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/4/22 16:16
 */
public interface RoleService {
    /**
     * 查询角色信息列表, 支持全部或分页
     * @param sysRoleQuery
     * @return
     */
    PageOutDTO<SysRoleDTO> list(SysRoleQuery sysRoleQuery);

    /**
     * 按id查询角色信息
     * @param roleId
     * @return
     */
    SysRoleDetailsDTO getRoleById(Long roleId);

    /**
     * 按id删除角色信息
     * @param roleId
     */
    void deleteById(Long roleId);

    /**
     * 修改角色信息
     * @param sysRoleDTO
     */
    void modify(SysRoleDTO sysRoleDTO);

    /**
     * 授予角色权限
     * @param sysRoleUpdateAuthoritiesDTO
     */
    void grant(SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO);

    /**
     * 收回角色的权限
     * @param sysRoleUpdateAuthoritiesDTO
     */
    void revoke(SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO);

    /**
     * 添加角色信息
     * @param sysRoleAddDTO
     */
    void addRole(SysRoleAddDTO sysRoleAddDTO);
}
