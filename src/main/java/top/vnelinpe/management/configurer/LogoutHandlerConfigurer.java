package top.vnelinpe.management.configurer;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
import top.vnelinpe.management.model.sys.SysUserDO;
import top.vnelinpe.management.util.JsonUtil;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/4/22 9:48
 */
@Configuration
public class LogoutHandlerConfigurer {
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
     * 配置注销处理器
     *
     * @return
     */
    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            threadLocalHolder.set(ShareVar.LOGOUT_START_TIME,System.currentTimeMillis());
            // 获取请求头的token
            String token = request.getHeader(ShareVar.REQUEST_TOKEN_KEY);
            try {
                // 解析token，包含黑名单判断
                UserDetailsDTO user = tokenHandler.getUserByToken(token);
                threadLocalHolder.set(ShareVar.USER_DETAILS,user);
                // 删除redis里的token相关信息
                tokenHandler.logout(user.getSerial());
            } catch (TokenExpiredException e) {
                // 这个情况是正常的,不用设置userkey到黑名单里
                // 当解析userkeytoken抛出这个异常时，threadlocal还没有设置，所以这一步再次设置一下
                threadLocalHolder.set(ShareVar.USER_TOKEN_KEY, "");
            } catch (Exception e) {
                // token本身就有问题，不用设置到黑名单，清空解析步骤设置的threadlocal，为了辨别接下来的logout返回数据
                threadLocalHolder.set(ShareVar.USER_TOKEN_KEY, null);
            }
        };
    }

    /**
     * 退出成功处理器,可以保证始终会进这个方法
     *
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            Object obj=threadLocalHolder.get(ShareVar.USER_TOKEN_KEY);
            ResultCode resultCode=ResultCode.SUCCESS;
            if(obj==null) {
                JsonUtil.writeResponse(response, ResultVO.success());
            }
            else{
                resultCode=ResultCode.TOKEN_OUT_OF_TIME;
                JsonUtil.writeResponse(response, ResultVO.fail(ResultCode.TOKEN_OUT_OF_TIME));
            }
            addLog(request,resultCode,(UserDetailsDTO) threadLocalHolder.get(ShareVar.USER_DETAILS));
        };
    }

    public void addLog(HttpServletRequest request, ResultCode resultCode, UserDetailsDTO userDetailsDTO){
        long after = System.currentTimeMillis();
        // 开始构造日志对象
        SysLogDO sysLog = new SysLogDO();
        // 设置服务端ip
        sysLog.setOptServerIp(serverIp);
        // 设置操作结果
        sysLog.setOptResult(resultCode.getMsg());
        // 设置执行时间
        sysLog.setOptLatency(after-(long)threadLocalHolder.get(ShareVar.LOGOUT_START_TIME));
        // 设置操作类型
        sysLog.setOptType(OperationType.LOGIN.getDesc());
        // 获取用户信息
        sysLog.setOptUsername(userDetailsDTO==null?"unknow":userDetailsDTO.getUsername());
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
