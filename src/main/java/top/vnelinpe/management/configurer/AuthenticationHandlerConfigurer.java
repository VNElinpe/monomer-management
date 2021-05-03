package top.vnelinpe.management.configurer;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import top.vnelinpe.management.annotation.LogHelper;
import top.vnelinpe.management.constant.OperationType;
import top.vnelinpe.management.constant.ResultCode;
import top.vnelinpe.management.core.ShareVar;
import top.vnelinpe.management.core.ThreadLocalHolder;
import top.vnelinpe.management.core.TokenHandler;
import top.vnelinpe.management.dto.sys.UserDetailsDTO;
import top.vnelinpe.management.model.sys.SysLogDO;
import top.vnelinpe.management.service.LogService;
import top.vnelinpe.management.util.RequestResponseUtil;
import top.vnelinpe.management.vo.sys.ResultVO;
import top.vnelinpe.management.util.JsonUtil;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * 自定义的spring security异常处理器
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 13:09
 */
@Configuration
public class AuthenticationHandlerConfigurer {
    private String serverIp;
    @Autowired
    private LogService logService;
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private ThreadLocalHolder threadLocalHolder;

    @PostConstruct
    private void init(){
        try {
            serverIp= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            serverIp="unknow";
        }
    }

    /**
     * 解决匿名用户访问无权限资源时的异常，未登录且没有权限
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                ResultVO result = null;
                if (authException instanceof InsufficientAuthenticationException) {
                    result = ResultVO.fail(ResultCode.DENY_ANNO);
                } else {
                    result = ResultVO.fail();
                }
                JsonUtil.writeResponse(response, result);
            }
        };
    }

    /**
     * 解决认证过的用户访问无权限资源时的异常,登陆但没有权限
     *
     * @return
     */
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            ResultVO result = null;
            // 缺少权限
            if (accessDeniedException instanceof AccessDeniedException) {
                result = ResultVO.fail(ResultCode.LEAK_AUTHORITY);
            } else {
                result = ResultVO.fail();
            }
            JsonUtil.writeResponse(response, result);
        };
    }

    /**
     * usernamePasswordAuthenticatinFilter的失败处理器
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            ResultVO<Object> result = null;
            // 请求不是POST方式
            if (exception instanceof AuthenticationServiceException) {
                result = ResultVO.fail(ResultCode.BAD_RQUEST_METHOD);
            }
            // 请求参数错误
            else if (exception instanceof AuthenticationCredentialsNotFoundException) {
                result = ResultVO.fail(ResultCode.BAD_REQUEST_ARGS);
            }
            // 用户名或密码错误
            else if (exception instanceof BadCredentialsException) {
                result = ResultVO.fail(ResultCode.BAD_USER_OR_PASSWORD);
            } else {
                result = ResultVO.fail();
            }
            JsonUtil.writeResponse(response, result);
            addLog(request,result.getMsg(), (String) threadLocalHolder.get(ShareVar.USER_DETAILS));
        };
    }


    /**
     * UsernamePasswordAuthenticationFilter操作成功的处理器
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            try {
                // 获取token
                String token = tokenHandler.generateTokenAndSingle();
                ResultVO<Object> result = ResultVO.success();
                result.setToken(token);
                JsonUtil.writeResponse(response, result);
            } catch (IOException e) {
                ResultVO<Object> result = ResultVO.fail(ResultCode.GENERATE_TOKEN_FAIL);
                JsonUtil.writeResponse(response, result);
            }
            addLog(request,ResultCode.SUCCESS.getMsg(), (String) threadLocalHolder.get(ShareVar.USER_DETAILS));
        };
    }

    public void addLog(HttpServletRequest request, String msg,String username){
        long after = System.currentTimeMillis();
        // 开始构造日志对象
        SysLogDO sysLog = new SysLogDO();
        // 设置服务端ip
        sysLog.setOptServerIp(serverIp);
        // 设置操作结果
        sysLog.setOptResult(msg);
        // 设置执行时间
        sysLog.setOptLatency(after-(long)threadLocalHolder.get(ShareVar.LOGIN_START_TIME));
        // 设置操作类型
        sysLog.setOptType(OperationType.LOGIN.getDesc());
        // 获取用户信息
        sysLog.setOptUsername(username==null?"unknow":username);
        // 获取客户端IP地址
        sysLog.setOptClientIp(RequestResponseUtil.getIPAddress(request));
        // 获取浏览器信息
        sysLog.setOptAgent(request.getHeader("User-Agent"));
        // 设置当前时间
        sysLog.setOptTime(LocalDateTime.now());
        // 记录日志
        logService.addLog(sysLog);
    }
}
