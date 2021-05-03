package top.vnelinpe.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.vnelinpe.management.dto.sys.SysRoleAddDTO;
import top.vnelinpe.management.dto.sys.SysRoleDTO;
import top.vnelinpe.management.dto.sys.SysRoleDetailsDTO;
import top.vnelinpe.management.dto.sys.SysRoleUpdateAuthoritiesDTO;
import top.vnelinpe.management.query.sys.SysRoleQuery;
import top.vnelinpe.management.vo.sys.SysRoleUpdateAuthoritiesVO;
import top.vnelinpe.management.vo.sys.SysRoleVO;
import top.vnelinpe.management.vo.sys.SysRoleDetailVO;

import java.util.List;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 22:28
 */
@Mapper
public interface RoleMapper {
    /**
     * 查询角色列表
     * @param sysRoleInDTO
     * @return
     */
    List<SysRoleDTO> listRole(SysRoleQuery sysRoleInDTO);

    /**
     * 按编号查询角色详细信息
     * @param roleId
     * @return
     */
    SysRoleDetailsDTO getRoleById(Long roleId);

    /**
     * 按编号删除角色信息
     * @param roleId
     */
    void deleteById(Long roleId);

    /**
     * 修改角色信息
     * @param sysRoleDTO
     */
    void modify(SysRoleDTO sysRoleDTO);

    /**
     * 添加角色的权限信息
     * @param sysRoleUpdateAuthoritiesDTO
     */
    void addAuthorities(SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO);

    /**
     * 收回角色的权限信息
     * @param sysRoleUpdateAuthoritiesDTO
     */
    void removeAuthorities(SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO);

    /**
     * 添加角色
     * @param sysRoleAddDTO
     */
    void addRole(SysRoleAddDTO sysRoleAddDTO);
}
