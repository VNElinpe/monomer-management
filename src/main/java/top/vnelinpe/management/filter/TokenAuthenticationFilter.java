package top.vnelinpe.management.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.vnelinpe.management.constant.ResultCode;
import top.vnelinpe.management.core.ShareVar;
import top.vnelinpe.management.core.TokenHandler;
import top.vnelinpe.management.dto.sys.UserDetailsDTO;
import top.vnelinpe.management.vo.sys.ResultVO;
import top.vnelinpe.management.model.sys.SysUserDO;
import top.vnelinpe.management.util.JsonUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于解析request中header的token
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/19 10:38
 */
@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenHandler tokenHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(ShareVar.REQUEST_TOKEN_KEY);
        if (token != null) {
            // 尝试解析userKeyToken，获取存在redis里的key
            try {
                // 根据userKey获取user
                UserDetailsDTO user = tokenHandler.getUserByToken(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (TokenExpiredException e) {
                log.debug("Token timeout, exception={}", e.getMessage());
                ResultVO<Object> result = ResultVO.fail(ResultCode.TOKEN_OUT_OF_TIME);
                JsonUtil.writeResponse(response, result);
                return;
            } catch (Exception e) {
                log.debug("Token parsing error, exception={}", e.getMessage());
                ResultVO<Object> result = ResultVO.fail(ResultCode.TOKEN_PARSE_FAIL);
                JsonUtil.writeResponse(response, result);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
