package top.vnelinpe.management.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.vnelinpe.management.annotation.LogHelper;
import top.vnelinpe.management.core.ThreadPoolHolder;
import top.vnelinpe.management.core.TokenHandler;
import top.vnelinpe.management.dto.sys.UserDetailsDTO;
import top.vnelinpe.management.vo.sys.ResultVO;
import top.vnelinpe.management.model.sys.SysLogDO;
import top.vnelinpe.management.model.sys.SysUserDO;
import top.vnelinpe.management.service.impl.LogServiceImpl;
import top.vnelinpe.management.util.RequestResponseUtil;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * 操作日志的切面
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/19 16:59
 */
@Slf4j
@Order(2)
@Aspect
@Component
public class LogAop {
    private String serverIp;

    @Autowired
    private LogServiceImpl logService;

    @Autowired
    private ThreadPoolHolder threadPoolHolder;

    @PostConstruct
    private void init(){
        try {
            serverIp=InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            serverIp="unknow";
        }
    }

    @Pointcut("@annotation(top.vnelinpe.management.annotation.LogHelper)")
    private void logHelper() {
    }

    @Around("logHelper()")
    public Object handleLog(ProceedingJoinPoint pjp) {
        Object proceed = ResultVO.fail();
        long before = System.currentTimeMillis();
        try {
            // 执行被代理的方法
            proceed = pjp.proceed();
            if (proceed instanceof ResultVO) {
                long after = System.currentTimeMillis();
                // 开始构造日志对象
                SysLogDO sysLog = new SysLogDO();
                // 设置服务端ip
                sysLog.setOptServerIp(serverIp);
                // 设置操作结果
                sysLog.setOptResult(((ResultVO)proceed).getMsg());
                // 设置执行时间
                sysLog.setOptLatency(after-before);
                // 开始获取操作类型
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                // 取得注解上的信息
                LogHelper annotation = signature.getMethod().getAnnotation(LogHelper.class);
                sysLog.setOptType(annotation.value().getDesc());
                // 获取用户信息
                UserDetailsDTO user = TokenHandler.getUser();
                sysLog.setOptUsername(user==null?"unknow":user.getUsername());
                // 获取客户端IP地址
                HttpServletRequest request = RequestResponseUtil.getRequest();
                sysLog.setOptClientIp(RequestResponseUtil.getIPAddress(request));
                // 获取浏览器信息
                sysLog.setOptAgent(request.getHeader("User-Agent"));
                // 设置当前时间
                sysLog.setOptTime(LocalDateTime.now());
                // 记录日志
                logService.addLog(sysLog);
            }
        } catch (Throwable throwable) {
            log.error("log aop error. {}",throwable.getMessage());
        } finally {
            return proceed;
        }
    }
}
