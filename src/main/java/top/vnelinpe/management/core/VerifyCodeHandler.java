package top.vnelinpe.management.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.vnelinpe.management.util.CommonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * 验证码工具类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 10:01
 */
@Component
public class VerifyCodeHandler {

    @Value("${verify-code.length}")
    private int verifyCodeLength;

    private final String[] set = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Autowired
    private TokenHandler tokenHandler;

    /**
     * 生成验证码
     *
     * @return
     */
    public Map<String, String> generateVerifyCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < verifyCodeLength; i++) {
            sb.append(random.nextInt(set.length));
        }
        String uuid = CommonUtil.generateUUID();
        String value = sb.toString();
        Map<String, String> map = new HashMap<>();
        map.put("key", uuid);
        map.put("value", value);
        map.put("expire", String.valueOf(tokenHandler.getExpireTime() / 60));
        tokenHandler.putKeyValueToRedis(tokenHandler.generateVerifyCodeKey(uuid), value);
        return map;
    }

    /**
     * 验证
     *
     * @param key
     * @param value
     * @return
     * @throws TimeoutException
     */
    public boolean verify(String key, String value) throws TimeoutException {
        String gkey = tokenHandler.generateVerifyCodeKey(key);
        Object fromRedis = tokenHandler.getFromRedis(gkey);
        // 超时
        if (fromRedis == null) {
            throw new TimeoutException();
        }

        if (fromRedis.equals(value)) {
            tokenHandler.removeFromRedis(gkey);
            return true;
        }
        return false;

    }
}
