package top.vnelinpe.management.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vnelinpe.management.dto.sys.PageOutDTO;
import top.vnelinpe.management.dto.sys.SysAuthorityDTO;
import top.vnelinpe.management.query.sys.SysAuthorityQuery;
import top.vnelinpe.management.query.sys.SysAuthorityPageQuery;
import top.vnelinpe.management.query.sys.SysRolePageQuery;
import top.vnelinpe.management.util.EnhancedBeanUtils;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.SysAuthorityVO;
import top.vnelinpe.management.mapper.AuthorityMapper;
import top.vnelinpe.management.service.AuthorityService;

import java.util.List;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 22:27
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public PageOutDTO<SysAuthorityDTO> list(SysAuthorityQuery sysAuthorityQuery) {
        if (sysAuthorityQuery instanceof SysAuthorityPageQuery && ((SysAuthorityPageQuery) sysAuthorityQuery).getPage() != null) {
            SysAuthorityPageQuery sysAuthorityPageInDTO = (SysAuthorityPageQuery) sysAuthorityQuery;
            PageHelper.startPage(sysAuthorityPageInDTO.getPage().getCurrentPage(), sysAuthorityPageInDTO.getPage().getPageSize());
        }
        List<SysAuthorityDTO> result = authorityMapper.listAuthorities(sysAuthorityQuery);
        Page page = null;
        if (sysAuthorityQuery instanceof SysAuthorityPageQuery && ((SysAuthorityPageQuery) sysAuthorityQuery).getPage() != null) {
            page = (Page) result;
        }
        return PageOutDTO.<SysAuthorityDTO>builder()
                .totalItems(page == null ? result.size() : page.getTotal())
                .totalPages(page == null ? 1 : page.getPages())
                .items(result)
                .build();
    }

    @Override
    public SysAuthorityDTO getAuthorityById(Long authorityId) {
        return authorityMapper.getAuthorityById(authorityId);
    }

    @Override
    public void deleteById(Long authorityId) {
        authorityMapper.deleteById(authorityId);
    }

    @Override
    public void modify(SysAuthorityDTO sysAuthorityDTO) {
        authorityMapper.modify(sysAuthorityDTO);
    }

    @Override
    public void addAuthority(SysAuthorityDTO sysAuthorityDTO) {
        authorityMapper.addAuthority(sysAuthorityDTO);
    }
}
