package top.vnelinpe.management.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 获取登录的用户的信息
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/20 10:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface LoginUser {
}
