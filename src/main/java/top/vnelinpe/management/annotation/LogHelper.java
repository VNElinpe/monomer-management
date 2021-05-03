package top.vnelinpe.management.annotation;

import top.vnelinpe.management.constant.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志的注解
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/19 17:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogHelper {
    OperationType value();
}
