package top.vnelinpe.management.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 纠正请求参数
 * 将空字符串、空数组、空集合设置成null
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/24 22:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EmptyField2Null {
}
