package top.vnelinpe.management.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.vnelinpe.management.core.ShareVar;
import top.vnelinpe.management.core.ThreadLocalHolder;
import top.vnelinpe.management.core.TokenHandler;
import top.vnelinpe.management.dto.sys.UserDetailsDTO;
import top.vnelinpe.management.vo.sys.ResultVO;
import top.vnelinpe.management.model.sys.SysUserDO;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * token相关的切面
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/19 14:13
 */
@Slf4j
@Order(0)
@Aspect
@Component
public class TokenAop {
    @Autowired
    private ThreadLocalHolder threadLocalHolder;

    @Autowired
    private TokenHandler tokenHandler;

    // 续期触发时间上限
    private long remindTime;

    @PostConstruct
    private void init() {
        remindTime = tokenHandler.getExpireTime() >> 1;
    }

    @Pointcut("execution(public * *..controller..*(..))")
    private void controller() {
    }

    /**
     * token自动续期，把本次token标记成无效，生成并返回一个新token
     *
     * @param result
     */
    @AfterReturning(value = "controller()", returning = "result")
    public void renewToken(Object result) {
        if (result instanceof ResultVO) {
            ResultVO resultDTO = (ResultVO) result;
            UserDetailsDTO user = TokenHandler.getUser();

            // controller的代码里已经处理过token了
            // 或者本身就是匿名方法，可以跳过后续代码
            if (user == null || resultDTO.getToken() != null) {
                return;
            }

            // 获取token的过期时间
            LocalDateTime expireTime = (LocalDateTime) threadLocalHolder.get(ShareVar.TOKEN_EXPIRE_TIME_KEY);
            // 满足续期条件
            if (LocalDateTime.now().plusSeconds(remindTime).isAfter(expireTime)) {
                // 生成新的token, 走的还是单点登录的流程，可以保证(如果token生成成功，那么之前的token的serial肯定不能用)
                String newToken = tokenHandler.generateTokenAndSingle();
                resultDTO.setToken(newToken);
            }
        }
    }
}
