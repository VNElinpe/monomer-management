package top.vnelinpe.management.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.vnelinpe.management.core.ShareVar;
import top.vnelinpe.management.core.ThreadLocalHolder;
import top.vnelinpe.management.filter.JsonUsernamePasswordAuthenticationFilter;
import top.vnelinpe.management.filter.TokenAuthenticationFilter;

/**
 * spring security配置类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 10:54
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private ThreadLocalHolder threadLocalHolder;

    /**
     * 安全配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // 禁用表单登录(/login)
                .formLogin()
                .disable()
                // 配置登录异常处理类
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                // 添加自定义过滤器
                .addFilterAt(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置security session，设置成不存储session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 配置权限
                .authorizeRequests()
                // 允许匿名访问
                .antMatchers("/swagger/**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/v2/**").anonymous()
                .antMatchers("/v2/api-docs-ext/**").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/doc.html").anonymous()
                .antMatchers("/file/**").anonymous()
                .antMatchers("/user/login").anonymous()
                .antMatchers("/user/code").anonymous()
                .antMatchers("/user/register").anonymous()
                // 此外，所有请求都必须认证
                .anyRequest().authenticated()
                .and()
                // 配置logout
                .logout()
                // logout的请求地址
                .logoutUrl(ShareVar.LOGOUT_URL)
                // logout的请求结果处理类
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler);
    }

    /**
     * 密码加解密工具
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置登录拦截器
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AbstractAuthenticationProcessingFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter jsonAuthFilter = new JsonUsernamePasswordAuthenticationFilter();
        jsonAuthFilter.setThreadLocalHolder(threadLocalHolder);
        jsonAuthFilter.setAuthenticationManager(getAuthenticationManager());
        jsonAuthFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        jsonAuthFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        return jsonAuthFilter;
    }
}
