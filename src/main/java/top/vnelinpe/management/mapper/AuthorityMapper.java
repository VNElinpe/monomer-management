package top.vnelinpe.management.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.vnelinpe.management.dto.sys.SysAuthorityDTO;
import top.vnelinpe.management.query.sys.SysAuthorityQuery;
import top.vnelinpe.management.vo.sys.SysAuthorityVO;

import java.util.List;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 22:28
 */
@Mapper
public interface AuthorityMapper {
    /**
     * 查询权限列表
     * @param sysAuthorityInDTO
     * @return
     */
    List<SysAuthorityDTO> listAuthorities(SysAuthorityQuery sysAuthorityInDTO);

    /**
     * 按id查询权限信息
     * @param authorityId
     * @return
     */
    SysAuthorityDTO getAuthorityById(Long authorityId);

    /**
     * 按id删除权限信息
     * @param authorityId
     */
    void deleteById(Long authorityId);

    /**
     * 修改权限信息
     * @param sysAuthorityDTO
     */
    void modify(SysAuthorityDTO sysAuthorityDTO);

    /**
     * 添加权限信息
     * @param sysAuthorityDTO
     */
    void addAuthority(SysAuthorityDTO sysAuthorityDTO);
}
