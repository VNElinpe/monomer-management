package top.vnelinpe.management.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vnelinpe.management.annotation.EmptyField2Null;
import top.vnelinpe.management.annotation.LogHelper;
import top.vnelinpe.management.constant.OperationType;
import top.vnelinpe.management.constant.ResultCode;
import top.vnelinpe.management.dto.sys.*;
import top.vnelinpe.management.query.sys.LongQuery;
import top.vnelinpe.management.query.sys.SysRoleQuery;
import top.vnelinpe.management.query.sys.SysRolePageQuery;
import top.vnelinpe.management.util.EnhancedBeanUtils;
import top.vnelinpe.management.vo.sys.*;
import top.vnelinpe.management.service.impl.RoleServiceImpl;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 22:25
 */
@Api(tags = "角色管理")
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @EmptyField2Null
    @ApiOperation("查询角色")
    @PostMapping("/list")
    public ResultVO<PageOutVO<SysRoleVO>> listRole(@RequestBody @Valid SysRolePageQuery sysRolePageQuery, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            PageOutDTO<SysRoleDTO> list = roleService.list(sysRolePageQuery);
            List<SysRoleVO> items= (List<SysRoleVO>) EnhancedBeanUtils.copyListProperties(list.getItems(),SysRoleVO::new);
            PageOutVO<SysRoleVO> result= PageOutVO.<SysRoleVO>builder()
                    .totalPages(list.getTotalPages())
                    .totalItems(list.getTotalItems())
                    .items(items)
                    .build();
            return ResultVO.success(result);
        } catch (Exception e) {
            log.error("Failed to list user. argument={}, exception={}",sysRolePageQuery,e.getMessage());
            return ResultVO.fail(ResultCode.LIST_USER_FAIL);
        }
    }

    @ApiOperation("查找角色")
    @PostMapping("/get")
    public ResultVO<SysRoleDetailVO> find(@RequestBody LongQuery roleId) {
        try {
            SysRoleDetailsDTO roleById = roleService.getRoleById(roleId.getId());
            if(roleById==null){
                return ResultVO.success();
           }
            SysRoleDetailVO sysRoleDetailVO = new SysRoleDetailVO();
            BeanUtils.copyProperties(roleById,sysRoleDetailVO);
            return ResultVO.success(sysRoleDetailVO);
        } catch (Exception e) {
            log.error("Failed to get user. argument={}, exception={}",roleId,e.getMessage());
            return ResultVO.fail(ResultCode.FIND_ROLE_FAIL);
        }
    }

    @LogHelper(OperationType.ADD_ROLE)
    @ApiOperation("添加角色")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid SysRoleAddVO sysRoleAddVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysRoleAddDTO sysRoleAddDTO = new SysRoleAddDTO();
            BeanUtils.copyProperties(sysRoleAddVO,sysRoleAddDTO);
            roleService.addRole(sysRoleAddDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to add role. argument={}, exception={}",sysRoleAddVO,e.getMessage());
            return ResultVO.fail(ResultCode.MODIFY_ROLE_FAIL);
        }
    }

    @LogHelper(OperationType.MODIFY_ROLE)
    @EmptyField2Null
    @ApiOperation("修改角色")
    @PostMapping("/modify")
    public ResultVO modify(@RequestBody @Valid SysRoleVO sysRoleVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysRoleDTO sysRoleDTO = new SysRoleDTO();
            BeanUtils.copyProperties(sysRoleVO,sysRoleDTO);
            roleService.modify(sysRoleDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to modify role. argument={}, exception={}",sysRoleVO,e.getMessage());
            return ResultVO.fail(ResultCode.MODIFY_ROLE_FAIL);
        }
    }

    @LogHelper(OperationType.GRANT_ROLE)
    @ApiOperation("授予角色权限")
    @PostMapping("/grant")
    public ResultVO grant(@RequestBody @Valid SysRoleUpdateAuthoritiesVO sysRoleUpdateAuthoritiesVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO = new SysRoleUpdateAuthoritiesDTO();
            BeanUtils.copyProperties(sysRoleUpdateAuthoritiesVO,sysRoleUpdateAuthoritiesDTO);
            roleService.grant(sysRoleUpdateAuthoritiesDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to grant permissions to role. argument={}, exception={}",sysRoleUpdateAuthoritiesVO,e.getMessage());
            return ResultVO.fail(ResultCode.GRANT_ROLE_FAIL);
        }
    }

    @LogHelper(OperationType.REVOKE_ROLE)
    @ApiOperation("收回角色权限")
    @PostMapping("/revoke")
    public ResultVO revoke(@RequestBody @Valid SysRoleUpdateAuthoritiesVO sysRoleUpdateAuthoritiesVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysRoleUpdateAuthoritiesDTO sysRoleUpdateAuthoritiesDTO = new SysRoleUpdateAuthoritiesDTO();
            BeanUtils.copyProperties(sysRoleUpdateAuthoritiesVO,sysRoleUpdateAuthoritiesDTO);
            roleService.revoke(sysRoleUpdateAuthoritiesDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to revoke permissions from role. argument={}, exception={}",sysRoleUpdateAuthoritiesVO,e.getMessage());
            return ResultVO.fail(ResultCode.GRANT_ROLE_FAIL);
        }
    }

    @LogHelper(OperationType.DELETE_ROLE)
    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public ResultVO delete(@RequestBody LongQuery roleId) {
        try {
            roleService.deleteById(roleId.getId());
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to delete role. argument={}, exception={}",roleId,e.getMessage());
            return ResultVO.fail(ResultCode.DELETE_ROLE_FAIL);
        }
    }

}
