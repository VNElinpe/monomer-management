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
import top.vnelinpe.management.dto.sys.PageOutDTO;
import top.vnelinpe.management.dto.sys.SysAuthorityDTO;
import top.vnelinpe.management.query.sys.LongQuery;
import top.vnelinpe.management.query.sys.SysAuthorityPageQuery;
import top.vnelinpe.management.util.EnhancedBeanUtils;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.ResultVO;
import top.vnelinpe.management.vo.sys.SysAuthorityAddVO;
import top.vnelinpe.management.vo.sys.SysAuthorityVO;
import top.vnelinpe.management.service.impl.AuthorityServiceImpl;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限管理
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 22:26
 */
@Api(tags = "权限管理")
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthorityServiceImpl authorityService;


    @EmptyField2Null
    @ApiOperation("查询权限")
    @PostMapping("/list")
    public ResultVO<PageOutVO<SysAuthorityVO>> listPerm(@RequestBody @Valid SysAuthorityPageQuery sysAuthorityPageQuery, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            PageOutDTO<SysAuthorityDTO> list = authorityService.list(sysAuthorityPageQuery);
            List<SysAuthorityVO> items= (List<SysAuthorityVO>) EnhancedBeanUtils.copyListProperties(list.getItems(),SysAuthorityVO::new);
            PageOutVO<SysAuthorityVO> result= PageOutVO.<SysAuthorityVO>builder()
                    .totalPages(list.getTotalPages())
                    .totalItems(list.getTotalItems())
                    .items(items)
                    .build();
            return ResultVO.success(result);
        } catch (Exception e) {
            log.error("Failed to list permissions. argument={}, exception={}",sysAuthorityPageQuery,e.getMessage());
            return ResultVO.fail(ResultCode.LIST_USER_FAIL);
        }
    }

    @ApiOperation("查找权限")
    @PostMapping("/find")
    public ResultVO<SysAuthorityVO> find(@RequestBody LongQuery authorityId) {
        try {
            SysAuthorityDTO authorityById = authorityService.getAuthorityById(authorityId.getId());
            if(authorityById==null){
                return ResultVO.success();
            }
            SysAuthorityVO sysAuthorityVO = new SysAuthorityVO();
            BeanUtils.copyProperties(authorityById,sysAuthorityVO);
            return ResultVO.success(sysAuthorityVO);
        } catch (Exception e) {
            log.error("Failed to get permissions. argument={}, exception={}",authorityId,e.getMessage());
            return ResultVO.fail(ResultCode.FIND_AUTHORITY_FAIL);
        }
    }

    @LogHelper(OperationType.ADD_AUTHORITY)
    @ApiOperation("添加权限")
    @PostMapping("/add")
    public ResultVO add(@RequestBody @Valid SysAuthorityAddVO sysAuthorityAddVO, BindingResult argr) {
        if(argr.hasErrors()){
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try{
            SysAuthorityDTO sysAuthorityDTO = new SysAuthorityDTO();
            BeanUtils.copyProperties(sysAuthorityAddVO,sysAuthorityDTO);
            authorityService.addAuthority(sysAuthorityDTO);
            return ResultVO.success();
        } catch (Exception e){
            log.error("Failed to add permission. argument={}, exception={}",sysAuthorityAddVO,e.getMessage());
            return ResultVO.fail(ResultCode.ADD_AUTHORITY_FAIL);
        }
    }

    @LogHelper(OperationType.MODIFY_AUTHORITY)
    @EmptyField2Null
    @ApiOperation("修改权限")
    @PostMapping("/modify")
    public ResultVO modify(@RequestBody @Valid SysAuthorityVO sysAuthorityVO, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            SysAuthorityDTO sysAuthorityDTO = new SysAuthorityDTO();
            BeanUtils.copyProperties(sysAuthorityVO,sysAuthorityDTO);
            authorityService.modify(sysAuthorityDTO);
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to modify permission. argument={}, exception={}",sysAuthorityVO,e.getMessage());
            return ResultVO.fail(ResultCode.MODIFY_AUTHORITY_FAIL);
        }
    }

    @LogHelper(OperationType.DELETE_AUTHORITY)
    @ApiOperation("删除权限")
    @PostMapping("/delete")
    public ResultVO delete(@RequestBody @Valid LongQuery authorityId, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            authorityService.deleteById(authorityId.getId());
            return ResultVO.success();
        } catch (Exception e) {
            log.error("Failed to delete permission. argument={}, exception={}",authorityId,e.getMessage());
            return ResultVO.fail(ResultCode.DELETE_AUTHORITY_FAIL);
        }
    }

}
