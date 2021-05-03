package top.vnelinpe.management.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vnelinpe.management.constant.ResultCode;
import top.vnelinpe.management.util.JVMUtil;
import top.vnelinpe.management.vo.sys.*;

/**
 * 系统信息接口
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 11:10
 */
@Api(tags = "系统管理")
@Slf4j
@RestController
@RequestMapping("/sys")
public class SystemController {

    @ApiOperation("获取类加载信息")
    @GetMapping("/class")
    public ResultVO<ClassLoadStatusVO> getClassLoadStatus() {
        try {
            return ResultVO.success(JVMUtil.getClassLoadStatusVO());
        } catch (Exception e) {
            log.error("Failed to get class load information，exception={}", e.getMessage());
            return ResultVO.fail(ResultCode.GET_CLASS_LOADING_FAIL);
        }
    }

    @ApiOperation("获取编译信息")
    @GetMapping("/compilation")
    public ResultVO<CompilationVO> getCompilation() {
        try {
            return ResultVO.success(JVMUtil.getCompilationVO());
        } catch (Exception e) {
            log.error("Failed to get compilation information，exception={}", e.getMessage());
            return ResultVO.fail(ResultCode.GET_COMPILATION_FAIL);
        }
    }

    @ApiOperation("获取内存使用信息")
    @GetMapping("/memory")
    public ResultVO<MemoryUsageVO> getMemoryUsage() {
        try {
            return ResultVO.success(JVMUtil.getMemoryVO());
        } catch (Exception e) {
            log.error("Failed to get memory usage information，exception={}", e.getMessage());
            return ResultVO.fail(ResultCode.GET_MEMORY_USAGE_FAIL);
        }
    }

    @ApiOperation("获取操作系统信息")
    @GetMapping("/os")
    public ResultVO<OperationSystemVO> getOperationSystem() {
        try {
            return ResultVO.success(JVMUtil.getOperationSystemVO());
        } catch (Exception e) {
            log.error("Failed to get operating system information，exception={}", e.getMessage());
            return ResultVO.fail(ResultCode.GET_OS_FAIL);
        }
    }

    @ApiOperation("获取运行时信息")
    @GetMapping("/runtime")
    public ResultVO<RuntimeVO> getRuntime() {
        try {
            return ResultVO.success(JVMUtil.getRuntimeVO());
        } catch (Exception e) {
            log.error("Failed to get runtime information，exception={}", e.getMessage());
            return ResultVO.fail(ResultCode.GET_RUNTIME_FAIL);
        }
    }

    @ApiOperation("获取线程信息")
    @GetMapping("/thread")
    public ResultVO<ThreadVO> getThread() {
        try {
            return ResultVO.success(JVMUtil.getThreadVO());
        } catch (Exception e) {
            log.error("Failed to get thread information，exception={}", e.getMessage());
            return ResultVO.fail(ResultCode.GET_THREAD_FAIL);
        }
    }
}
