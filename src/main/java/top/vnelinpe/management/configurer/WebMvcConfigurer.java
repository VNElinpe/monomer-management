package top.vnelinpe.management.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import top.vnelinpe.management.annotation.support.LoginUserHandlerMethodArgumentResolver;
import top.vnelinpe.management.util.CommonUtil;

import java.util.List;

/**
 * mvc配置
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/20 11:22
 */
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
    @Value("${file-access.access-pattern}")
    private String accessPattern;

    @Value("${file-access.file-path}")
    private String filePath;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserHandlerMethodArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CommonUtil.makeDirExists(filePath);
        registry.addResourceHandler(accessPattern).addResourceLocations("file:" + filePath);
    }
}
