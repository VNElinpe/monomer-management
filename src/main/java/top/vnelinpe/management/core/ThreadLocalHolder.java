package top.vnelinpe.management.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/19 9:57
 */
@Component
public class ThreadLocalHolder {
    public static final ThreadLocal<Map> threadLocal = new ThreadLocal<>();

    public void set(String key, Object value) {
        Map map = threadLocal.get();
        if (map == null) {
            map = new HashMap();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public Object get(String key) {
        Map map = threadLocal.get();
        return map == null ? null : map.get(key);
    }

    public void remove() {
        threadLocal.remove();
    }
}
