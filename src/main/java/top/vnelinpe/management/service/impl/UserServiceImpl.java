package top.vnelinpe.management.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.vnelinpe.management.dto.sys.*;
import top.vnelinpe.management.query.sys.LongQuery;
import top.vnelinpe.management.query.sys.StringQuery;
import top.vnelinpe.management.util.EnhancedBeanUtils;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.SysUserVO;
import top.vnelinpe.management.vo.sys.SysUserDetailVO;
import top.vnelinpe.management.model.sys.SysUserDO;
import top.vnelinpe.management.mapper.UserMapper;
import top.vnelinpe.management.query.sys.SysUserPageQuery;
import top.vnelinpe.management.query.sys.SysUserQuery;
import top.vnelinpe.management.service.UserService;
import top.vnelinpe.management.vo.sys.SysUserAddVO;
import top.vnelinpe.management.vo.sys.SysUserUpdateRoleVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户相关的service
 * 登录、
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 9:31
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsDTO user = null;
        if (!StringUtils.hasText(username) || (user=userMapper.getUserByUsername(username))==null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user;
    }

    @Override
    public SysUserDetailsDTO getUserById(Long userId) {
        // 查询除额外的权限之外的所有信息
        SysUserDetailsDTO userById = userMapper.getUserById(userId);
        // 查询用户额外的权限信息
        if(userById!=null) {
            userById.setAuthorities(userMapper.listExtraAuthorityByUserId(userId));
        }
        return userById;
    }

    @Override
    public PageOutDTO<SysUserDTO> list(SysUserQuery sysUserQuery) {
        if (sysUserQuery instanceof SysUserPageQuery && ((SysUserPageQuery) sysUserQuery).getPage() != null) {
            SysUserPageQuery sysUserPageInDTO = (SysUserPageQuery) sysUserQuery;
            PageHelper.startPage(sysUserPageInDTO.getPage().getCurrentPage(), sysUserPageInDTO.getPage().getPageSize());
        }
        List<SysUserDTO> result = userMapper.listUser(sysUserQuery);
        Page page = null;
        if (sysUserQuery instanceof SysUserPageQuery && ((SysUserPageQuery) sysUserQuery).getPage() != null) {
            page = (Page) result;
        }
        return PageOutDTO.<SysUserDTO>builder()
                .totalItems(page == null ? result.size() : page.getTotal())
                .totalPages(page == null ? 1 : page.getPages())
                .items(result)
                .build();
    }

    @Override
    public void deleteById(Long userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public void modify(SysUserUpdateDTO sysUserUpdateDTO) {
        userMapper.modify(sysUserUpdateDTO);
    }

    @Override
    @Transactional
    public void grant(SysUserUpdateRoleDTO sysUserUpdateRoleDTO) {
        // 添加角色
        if(sysUserUpdateRoleDTO.getRoles()!=null) {
            userMapper.addRoles(sysUserUpdateRoleDTO.getId(), sysUserUpdateRoleDTO.getRoles());
        }
        // 添加额外权限
        if(sysUserUpdateRoleDTO.getAuthorities()!=null){
            userMapper.addAuthorities(sysUserUpdateRoleDTO.getId(), sysUserUpdateRoleDTO.getAuthorities());
        }
    }

    @Override
    @Transactional
    public void revoke(SysUserUpdateRoleDTO sysUserUpdateRoleDTO) {
        // 添加角色
        if(sysUserUpdateRoleDTO.getRoles()!=null) {
            userMapper.removeRoles(sysUserUpdateRoleDTO.getId(), sysUserUpdateRoleDTO.getRoles());
        }
        // 添加额外权限
        if(sysUserUpdateRoleDTO.getAuthorities()!=null){
            userMapper.removeAuthorities(sysUserUpdateRoleDTO.getId(), sysUserUpdateRoleDTO.getAuthorities());
        }
    }

    @Override
    @Transactional
    public void addUser(SysUserAddDTO sysUserAddDTO) {
        userMapper.addUser(sysUserAddDTO);
        // 构建添加用户角色权限的dto
        SysUserUpdateRoleDTO sysUserUpdateRoleDTO = new SysUserUpdateRoleDTO();
        BeanUtils.copyProperties(sysUserAddDTO,sysUserUpdateRoleDTO);
        // 添加用户角色权限信息，继承事务
        grant(sysUserUpdateRoleDTO);
    }

    @Override
    public SysUserDTO findUserByEmail(String email) {
        SysUserDO userByEmail = userMapper.findUserByEmail(email);
        if(userByEmail==null){
            return null;
        }
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(userByEmail,sysUserDTO);
        return sysUserDTO;
    }

    @Override
    public UserDetailsDTO getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public SysUserDTO findUserByUsername(String username) {
        SysUserDO userByUsername = userMapper.findUserByUsername(username);
        if(userByUsername==null){
            return null;
        }
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(userByUsername,sysUserDTO);
        return sysUserDTO;
    }

    @Override
    public SysUserDTO findUserBySurname(String surname) {
        SysUserDO userBySurname = userMapper.findUserBySurname(surname);
        if(userBySurname==null){
            return null;
        }
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(userBySurname,sysUserDTO);
        return sysUserDTO;
    }

    @Override
    public SysUserDTO findUserByPhone(String phone) {
        SysUserDO userByPhone = userMapper.findUserByPhone(phone);
        if(userByPhone==null){
            return null;
        }
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(userByPhone,sysUserDTO);
        return sysUserDTO;
    }
}
