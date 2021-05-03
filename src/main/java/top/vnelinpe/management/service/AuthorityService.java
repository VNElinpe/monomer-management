package top.vnelinpe.management.service;

import top.vnelinpe.management.dto.sys.PageOutDTO;
import top.vnelinpe.management.dto.sys.SysAuthorityDTO;
import top.vnelinpe.management.query.sys.SysAuthorityQuery;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.SysAuthorityVO;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/4/22 16:18
 */
public interface AuthorityService {
    /**
     * 查询权限列表，支持全部或分页
     * @param sysAuthorityQuery
     * @return
     */
    PageOutDTO<SysAuthorityDTO> list(SysAuthorityQuery sysAuthorityQuery);

    /**
     * 按id查询权限信息
     * @param authorityId
     * @return
     */
    SysAuthorityDTO getAuthorityById(Long authorityId);

    /**
     * 按编号删除权限
     * @param authorityId
     */
    void deleteById(Long authorityId);

    /**
     * 修改权限信息
     * @param sysAuthorityDTO
     */
    void modify(SysAuthorityDTO sysAuthorityDTO);

    /**
     * 添加权限
     * @param sysAuthorityDTO
     */
    void addAuthority(SysAuthorityDTO sysAuthorityDTO);
}
