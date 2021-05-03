package top.vnelinpe.management.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.vnelinpe.management.constant.ResultCode;
import top.vnelinpe.management.vo.sys.ResultVO;

/**
 * 全局异常处理类
 * @author VNElinpe
 * @version 1.0
 * @date 2021/4/22 17:11
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e){
        // 处理在Controller中没有捕获到的异常
        if(e instanceof HttpRequestMethodNotSupportedException){
            return ResultVO.fail(ResultCode.BAD_RQUEST_METHOD);
        }
        else {
            return ResultVO.fail();
        }
    }
}
