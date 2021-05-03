package top.vnelinpe.management.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.vnelinpe.management.core.ThreadLocalHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 清理资源
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/22 23:43
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CleanerFilter extends OncePerRequestFilter {
    @Autowired
    private ThreadLocalHolder threadLocalHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
        threadLocalHolder.remove();
    }
}
