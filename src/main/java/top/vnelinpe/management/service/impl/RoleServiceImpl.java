package top.vnelinpe.management.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.vnelinpe.management.dto.sys.*;
import top.vnelinpe.management.query.sys.SysRoleQuery;
import top.vnelinpe.management.query.sys.SysRolePageQuery;
import top.vnelinpe.management.query.sys.SysUserPageQuery;
import top.vnelinpe.management.util.EnhancedBeanUtils;
import top.vnelinpe.management.vo.sys.SysRoleUpdateAuthoritiesVO;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.SysRoleVO;
import top.vnelinpe.management.vo.sys.SysRoleDetailVO;
import top.vnelinpe.management.mapper.RoleMapper;
import top.vnelinpe.management.service.RoleService;

import java.util.List;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 22:27
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageOutDTO<SysRoleDTO> list(SysRoleQuery sysRoleQuery) {
        if (sysRoleQuery instanceof SysRolePageQuery && ((SysRolePageQuery) sysRoleQuery).getPage() != null) {
            SysRolePageQuery sysRolePageInDTO = (SysRolePageQuery) sysRoleQuery;
            PageHelper.startPage(sysRolePageInDTO.getPage().getCurrentPage(), sysRolePageInDTO.getPage().getPageSize());
        }
        List<SysRoleDTO> result = roleMapper.listRole(sysRoleQuery);
        Page page = null;
        if (sysRoleQuery instanceof SysRolePageQuery && ((SysRolePageQuery) sysRoleQuery).getPage() != null) {
            page = (Page) result;
        }
        return PageOutDTO.<SysRoleDTO>builder()
                .totalItems(page == null ? result.size() : page.getTotal())
                .totalPages(page == null ? 1 : page.getPages())
                .items(result)
                .build();
    }

    @Override
    public SysRoleDetailsDTO getRoleById(Long roleId) {
        return roleMapper.getRoleById(roleId);
    }

    @Override
    public void deleteById(Long roleId) {
        roleMapper.deleteById(roleId);
    }

    @Override
    public void modify(SysRoleDTO sysRoleDTO) {
        roleMapper.modify(sysRoleDTO);
    }

    @Override
    public void grant(SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO) {
        roleMapper.addAuthorities(sysRoleUpdateAuthoritiesDTO);
    }

    @Override
    public void revoke(SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO) {
        roleMapper.removeAuthorities(sysRoleUpdateAuthoritiesDTO);
    }

    @Override
    @Transactional
    public void addRole(SysRoleAddDTO sysRoleAddDTO) {
        roleMapper.addRole(sysRoleAddDTO);
        if(sysRoleAddDTO.getAuthorities()==null){
            return;
        }
        SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO = new SysRoleUpdateAuthoritiesDTO();
        BeanUtils.copyProperties(sysRoleAddDTO,sysRoleUpdateAuthoritiesDTO);
        grant(sysRoleUpdateAuthoritiesDTO);
    }
}
