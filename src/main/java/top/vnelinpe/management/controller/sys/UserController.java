package top.vnelinpe.management.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vnelinpe.management.annotation.EmptyField2Null;
import top.vnelinpe.management.annotation.LogHelper;
import top.vnelinpe.management.constant.OperationType;
import top.vnelinpe.management.constant.ResultCode;
import top.vnelinpe.management.core.ThreadPoolHolder;
import top.vnelinpe.management.core.VerifyCodeHandler;
import top.vnelinpe.management.core.notify.UserNotifier;
import top.vnelinpe.management.dto.sys.*;
import top.vnelinpe.management.util.EnhancedBeanUtils;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.ResultVO;
import top.vnelinpe.management.vo.sys.SysUserVO;
import top.vnelinpe.management.vo.sys.SysUserDetailVO;
import top.vnelinpe.management.query.sys.LongQuery;
import top.vnelinpe.management.query.sys.StringQuery;
import top.vnelinpe.management.query.sys.SysUserPageQuery;
import top.vnelinpe.management.service.impl.UserServiceImpl;
import top.vnelinpe.management.vo.sys.LoginVO;
import top.vnelinpe.management.vo.sys.SysUserAddVO;
import top.vnelinpe.management.vo.sys.SysUserUpdateRoleVO;
import top.vnelinpe.management.vo.sys.SysUserUpdateVO;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户相关服务
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 10:50
 */
@Api(tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ApiOperation("登录")
    @PostMapping("/login")
    public ResultVO login(@RequestBody LoginVO loginVO) {
        return null;
    }

    @ApiOperation("注销")
    @PostMapping("/logout")
    public ResultVO logout() {
        return null;
    }


    @EmptyField2Null
    @ApiOperation("查询用户列表")
    @PostMapping("/list")
    public ResultVO<PageOutVO<SysUserVO>> listUser(@RequestBody @Valid SysUserPageQuery sysUserPageQuery, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            PageOutDTO<SysUserDTO> list = userService.list(sysUserPageQuery);
            List<SysUserVO> items= (List<SysUserVO>) EnhancedBeanUtils.copyListProperties(list.getItems(),SysUserVO::new);
            PageOutVO<SysUserVO> result = PageOutVO.<SysUserVO>builder()
                    .totalPages(list.getTotalPages())
                    .totalItems(list.getTotalItems())
                    .items(items)
                    .build();
            return ResultVO.success(result);
        } catch (Exception e) {
            log.error("Failed to list user. arguments={}, exception={}",sysUserPageQuery,e.getMessage());
            return ResultVO.fail(ResultCode.LIST_USER_FAIL);
        }
    }

    @ApiOperation("获取用户信息")
    @PostMapping("/get")
    public ResultVO<SysUserDetailVO> find(@RequestBody @Valid LongQuery userId, BindingResult argr) {
        if(argr.hasErrors()){
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysUserDetailsDTO userById = userService.getUserById(userId.getId());
            if(userById==null){
                return ResultVO.success();
            }
            SysUserDetailVO sysUserDetailVO = new SysUserDetailVO();
            BeanUtils.copyProperties(userById,sysUserDetailVO);
            return ResultVO.success(sysUserDetailVO);
        } catch (Exception e) {
            log.error("Failed to get user. arguments={}, exception={}",userId,e.getMessage());
            return ResultVO.fail(ResultCode.FIND_USER_FAIL);
        }
    }

    @EmptyField2Null
    @ApiOperation("添加用户")
    @PostMapping("/add")
    public ResultVO register(@RequestBody @Valid SysUserAddVO sysUserAddVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            if (userService.findUserByUsername(sysUserAddVO.getUsername()) != null) {
                return ResultVO.fail(ResultCode.USERNAME_DUPLICATED);
            }
            if (userService.findUserBySurname(sysUserAddVO.getSurname()) != null) {
                return ResultVO.fail(ResultCode.SURNAME_DUPLICATED);
            }
            // 密码加密
            sysUserAddVO.setPassword(passwordEncoder.encode(sysUserAddVO.getPassword()));
            // 添加用户
            SysUserAddDTO sysUserAddDTO = new SysUserAddDTO();
            BeanUtils.copyProperties(sysUserAddVO,sysUserAddDTO);
            userService.addUser(sysUserAddDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to add user. arguments={}, exception={}",sysUserAddVO,e.getMessage());
            return ResultVO.fail(ResultCode.ADD_USER_FAIL);
        }
    }

    @ApiOperation("检查用户名是否重复")
    @PostMapping("/dusername")
    public ResultVO duplicateUsername(@RequestBody @Valid StringQuery username, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysUserDTO userByUsername = userService.findUserByUsername(username.getStr());
            if (userByUsername == null) {
                return ResultVO.success();
            } else {
                return ResultVO.fail(ResultCode.USERNAME_DUPLICATED);
            }
        } catch (Exception e) {
            log.error("Detects the username for repeated failures. arguments={}, exception={}",username,e.getMessage());
            return ResultVO.fail(ResultCode.FIND_USER_FAIL);
        }
    }

    @ApiOperation("检查昵称是否重复")
    @PostMapping("/dsurname")
    public ResultVO duplicateSurname(@RequestBody @Valid StringQuery surname, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysUserDTO userBySurname = userService.findUserBySurname(surname.getStr());
            if (userBySurname == null) {
                return ResultVO.success();
            } else {
                return ResultVO.fail(ResultCode.SURNAME_DUPLICATED);
            }
        } catch (Exception e) {
            log.error("Detects the surname for repeated failures. arguments={}, exception={}",surname,e.getMessage());
            return ResultVO.fail(ResultCode.FIND_USER_FAIL);
        }
    }

    @ApiOperation("检查邮箱是否重复")
    @PostMapping("/dmail")
    public ResultVO duplicateMail(@RequestBody @Valid StringQuery email, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysUserDTO userByEmail = userService.findUserByEmail(email.getStr());
            if (userByEmail == null) {
                return ResultVO.success();
            } else {
                return ResultVO.fail(ResultCode.MAIL_DUPLICATED);
            }
        } catch (Exception e) {
            log.error("Detects the email for repeated failures. arguments={}, exception={}",email,e.getMessage());
            return ResultVO.fail(ResultCode.FIND_USER_FAIL);
        }
    }

    @ApiOperation("检查手机号是否重复")
    @PostMapping("/dphone")
    public ResultVO duplicatePhone(@RequestBody @Valid StringQuery phone, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysUserDTO userByPhone = userService.findUserByPhone(phone.getStr());
            if (userByPhone == null) {
                return ResultVO.success();
            } else {
                return ResultVO.fail(ResultCode.PHONE_DUPLICATED);
            }
        } catch (Exception e) {
            log.error("Detects the number for repeated failures. arguments={}, exception={}",phone,e.getMessage());
            return ResultVO.fail(ResultCode.FIND_USER_FAIL);
        }
    }

    @LogHelper(OperationType.MODIFY_USER)
    @EmptyField2Null
    @ApiOperation("修改用户")
    @PostMapping("/modify")
    public ResultVO modify(@RequestBody @Valid SysUserUpdateVO sysUserUpdateVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        // 密码加密
        if (sysUserUpdateVO.getPassword() != null) {
            sysUserUpdateVO.setPassword(passwordEncoder.encode(sysUserUpdateVO.getPassword()));
        }
        try {
            SysUserUpdateDTO sysUserUpdateDTO = new SysUserUpdateDTO();
            BeanUtils.copyProperties(sysUserUpdateVO,sysUserUpdateDTO);
            userService.modify(sysUserUpdateDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to modify user. arguments={}, exception={}",sysUserUpdateVO,e.getMessage());
            return ResultVO.fail(ResultCode.MODIFY_USER_FAIL);
        }
    }

    @LogHelper(OperationType.GRANT_USER)
    @EmptyField2Null
    @ApiOperation("授予用户角色权限")
    @PostMapping("/grant")
    public ResultVO grant(@RequestBody @Valid SysUserUpdateRoleVO sysUserUpdateRoleVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysUserUpdateRoleDTO sysUserUpdateRoleDTO = new SysUserUpdateRoleDTO();
            BeanUtils.copyProperties(sysUserUpdateRoleVO,sysUserUpdateRoleDTO);
            userService.grant(sysUserUpdateRoleDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to grant user roles or additional permissions. arguments={}, exception={}",sysUserUpdateRoleVO,e.getMessage());
            return ResultVO.fail(ResultCode.GRANT_ROLE_FAIL);
        }
    }

    @LogHelper(OperationType.REVOKE_USER)
    @EmptyField2Null
    @ApiOperation("收回用户角色权限")
    @PostMapping("/revoke")
    public ResultVO revoke(@RequestBody @Valid SysUserUpdateRoleVO sysUserUpdateRoleVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysUserUpdateRoleDTO sysUserUpdateRoleDTO = new SysUserUpdateRoleDTO();
            BeanUtils.copyProperties(sysUserUpdateRoleVO,sysUserUpdateRoleDTO);
            userService.revoke(sysUserUpdateRoleDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to revoke user role or additional permissions. arguments={}, exception={}",sysUserUpdateRoleVO,e.getMessage());
            return ResultVO.fail(ResultCode.GRANT_ROLE_FAIL);
        }
    }

    @LogHelper(OperationType.DELETE_USER)
    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public ResultVO delete(@RequestBody @Valid LongQuery userId, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            userService.deleteById(userId.getId());
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to delete user. arguments={}, exception={}",userId,e.getMessage());
            return ResultVO.fail(ResultCode.DELETE_USER_FAIL);
        }
    }
}
