package top.vnelinpe.management.filter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.vnelinpe.management.core.ShareVar;
import top.vnelinpe.management.core.ThreadLocalHolder;
import top.vnelinpe.management.vo.sys.LoginVO;
import top.vnelinpe.management.util.JsonUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的UsernamePasswordAuthenticationFilter
 * 通过json的方式来登录
 * 需要用到authenticationManager,所以不能直接放在容器里
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 13:24
 */
@Slf4j
public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Setter
    private ThreadLocalHolder threadLocalHolder;
    private WebAuthenticationDetailsSource detailsSource=new WebAuthenticationDetailsSource();

    public JsonUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher(ShareVar.LOGIN_URL, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        threadLocalHolder.set(ShareVar.LOGIN_START_TIME,System.currentTimeMillis());
        // 请求不是Post
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("Only suppot POST");
        }
        LoginVO loginDTO = null;
        // 读取请求体里的参数
        try (ServletInputStream inputStream = request.getInputStream()) {
            loginDTO = JsonUtil.parse(inputStream, LoginVO.class);
        } catch (IOException e) {
            log.error("Failed to read user information at login, exception={}", e.getMessage());
        }

        // 参数有误
        if (loginDTO == null || loginDTO.getUsername() == null || loginDTO.getPassword() == null) {
            throw new AuthenticationCredentialsNotFoundException("Bad request arguments");
        }
        threadLocalHolder.set(ShareVar.USER_DETAILS,loginDTO.getUsername());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginDTO.getUsername().trim(), loginDTO.getPassword().trim());

        // Allow subclasses to set the "details" property
        authRequest.setDetails(detailsSource.buildDetails(request));

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
