package top.vnelinpe.management.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * 将@RequestBody注解的对象中空内容字段设置成null
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/24 22:56
 */
@Slf4j
@Order(1)
@Aspect
@Component
public class Empty2NullAop {
    private final List<String> excpetClassName = List.of(
            "java.lang.Integer",
            "java.lang.Double",
            "java.lang.Float",
            "java.lang.Long",
            "java.lang.Short",
            "java.lang.Byte",
            "java.lang.Boolean",
            "java.lang.Character");

    @Pointcut("@annotation(top.vnelinpe.management.annotation.EmptyField2Null)")
    private void empty() {
    }

    @Before("empty()")
    public void handleEmpty(JoinPoint jp) {
        Object[] args = jp.getArgs();
        // 空参数方法
        if (args.length == 0) {
            return;
        }
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                // 只处理加了@RequestBody注解的对象
                if (parameterAnnotations[i][j].annotationType().equals(RequestBody.class)) {
                    handleClassFieldsEmpty(args[i].getClass(), args[i]);
                    break;
                }
            }
        }

    }

    /**
     * 处理一个对象的空字段("" -> null)
     *
     * @param aClass 类，可以是obj的父类，利用java内省可以处理父类的字段
     * @param obj 具体对象
     * @return 如果对象的所有字段都为null，就返回true
     */
    private boolean handleClassFieldsEmpty(Class<?> aClass, Object obj) {
        // 获取本类(aClass)定义的所有字段，不包括aClass的父类字段。但是aClass可以是obj的父类
        Field[] declaredFields = aClass.getDeclaredFields();
        int nullCounter=0;
        for (Field declaredField : declaredFields) {
            Class<?> type = declaredField.getType();
            // 基本类型直接跳过
            if (type.isPrimitive()) {
                continue;
            }
            // 尝试获取本字段的读写方法操作工具类
            PropertyDescriptor propertyDescriptor = null;
            try {
                propertyDescriptor = new PropertyDescriptor(declaredField.getName(), aClass);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            if (propertyDescriptor == null) {
                continue;
            }
            // 如果本字段没有读方法，就结束本字段的流程
            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod == null) {
                continue;
            }
            try {
                // 调用getter获取字段值
                Object value = readMethod.invoke(obj);
                boolean shouldBeNull = true;
                // 本字段本身就是null，统计null的个数，继续处理下一个字段
                if (value == null) {
                    nullCounter++;
                    continue;
                }
                // 基本类型的包装类也直接跳过
                if (excpetClassName.contains(type.getName())) {
                    continue;
                }
                // 字符串类型
                if (type.equals(String.class)) {
                    // 不是空字符串，处理下一个字段
                    if (!((String) value).isEmpty()) {
                        continue;
                    }
                }
                // 集合类型
                else if (Collection.class.isAssignableFrom(type)) {
                    // 不是空集合，处理下一个字段
                    if (!((Collection) value).isEmpty()) {
                        continue;
                    }
                }
                // 数组类型
                else if (type.isArray()) {
                    // 不是空数组，处理下一个字段
                    if (Array.getLength(value) != 0) {
                        continue;
                    }
                }
                // 自定义的其他对象
                else {
                    // 递归处理
                    shouldBeNull=handleClassFieldsEmpty(type, value);
                }

                // 将值设置成null
                if (shouldBeNull) {
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    if (writeMethod != null) {
                        nullCounter++;
                        writeMethod.invoke(obj, (Object) null);
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("empty -> null error. {}",e.getMessage());
            }
        }
        // 继承了父类，处理从父类继承的字段
        if(!aClass.getSuperclass().equals(Object.class)){
            if(!handleClassFieldsEmpty(aClass.getSuperclass(),obj)){
                return false;
            }
        }
        return nullCounter == declaredFields.length;
    }
}