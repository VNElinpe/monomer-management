package top.vnelinpe.management.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vnelinpe.management.annotation.EmptyField2Null;
import top.vnelinpe.management.constant.ResultCode;
import top.vnelinpe.management.dto.sys.PageOutDTO;
import top.vnelinpe.management.dto.sys.SysOptLogDTO;
import top.vnelinpe.management.query.sys.OptLogQuery;
import top.vnelinpe.management.query.sys.OptLogPageQuery;
import top.vnelinpe.management.util.EnhancedBeanUtils;
import top.vnelinpe.management.vo.sys.OptLogVO;
import top.vnelinpe.management.vo.sys.PageOutVO;
import top.vnelinpe.management.vo.sys.ResultVO;
import top.vnelinpe.management.service.impl.LogServiceImpl;
import top.vnelinpe.management.util.CommonUtil;
import top.vnelinpe.management.util.ExcelUtil;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.List;

/**
 * 操作日志相关接口
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 14:11
 */
@Api(tags = "日志管理")
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {
    @Value("${file-access.file-path}")
    private String filePath;

    @Value("${file-access.excel-path}")
    private String excelPath;

    @Value("${file-access.access-pattern}")
    private String accessPattern;

    @Autowired
    private LogServiceImpl logService;

    @EmptyField2Null
    @ApiOperation("查询日志")
    @PostMapping("/list")
    public ResultVO<PageOutVO<OptLogVO>> listLog(@RequestBody @Valid OptLogPageQuery optLogPageQuery, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            PageOutDTO<SysOptLogDTO> list = logService.list(optLogPageQuery);
            List<OptLogVO> items= (List<OptLogVO>) EnhancedBeanUtils.copyListProperties(list.getItems(),OptLogVO::new);
            PageOutVO<OptLogVO> result= PageOutVO.<OptLogVO>builder()
                    .totalPages(list.getTotalPages())
                    .totalItems(list.getTotalItems())
                    .items(items)
                    .build();
            return ResultVO.success(result);
        } catch (Exception e) {
            log.error("Failed to list operation log. argument={}, exception={}",optLogPageQuery,e.getMessage());
            return ResultVO.fail(ResultCode.LIST_LOG_FAIL);
        }
    }

    @EmptyField2Null
    @ApiOperation("导出日志")
    @PostMapping("/dump")
    public ResultVO dumpLog(@RequestBody @Valid OptLogQuery optLogQuery, BindingResult argr) {
        if (argr.hasErrors()) {
            return ResultVO.fail(ResultCode.BAD_REQUEST_ARGS, argr.getFieldError().getDefaultMessage());
        }
        try {
            PageOutDTO<SysOptLogDTO> list = logService.list(optLogQuery);
            List<OptLogVO> items= (List<OptLogVO>) EnhancedBeanUtils.copyListProperties(list.getItems(),OptLogVO::new);

            String fileName = CommonUtil.generateUUID() + ".xlsx";
            ExcelUtil.write(Path.of(filePath, excelPath, fileName).toString(), items, OptLogVO.class);
            return ResultVO.success(Path.of(accessPattern.replaceAll("\\*", ""), excelPath, fileName).toString().replaceAll("\\\\+", "/"));
        } catch (Exception e) {
            log.error("Failed to dump operation log. argument={}, exception={}",optLogQuery,e.getMessage());
            return ResultVO.fail(ResultCode.DUMP_LOG_FAIL);
        }
    }
}