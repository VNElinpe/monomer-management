package top.vnelinpe.management.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 16:31
 */
public class EnhancedBeanUtils extends BeanUtils {
    /**
     * 浅拷贝集合
     * @param source 来源集合
     * @param supplier 获取目标集合的单个元素(空构造方法)
     * @param <S>
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <S, T> Collection<T> copyListProperties(Collection<S> source, Supplier<T> supplier) throws BeansException {
        return copyListProperties(source,supplier,null);
    }

    /**
     * 浅拷贝集合
     * @param source 来源集合
     * @param supplier 获取目标集合的单个元素(空构造方法)
     * @param biConsumer 自定义的属性设置方法
     * @param <S>
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <S, T> Collection<T> copyListProperties(Collection<S> source, Supplier<T> supplier, BiConsumer<S,T> biConsumer) throws BeansException {
        Collection collection = null;
        try {
            collection = source.getClass().getConstructor().newInstance();
            for (S s : source) {
                T t = supplier.get();
                copyProperties(s,t);
                if(biConsumer!=null){
                    biConsumer.accept(s,t);
                }
                collection.add(t);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return collection;
    }
}
