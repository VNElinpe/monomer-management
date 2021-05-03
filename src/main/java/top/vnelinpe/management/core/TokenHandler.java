package top.vnelinpe.management.core;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.Setter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import top.vnelinpe.management.dto.sys.UserDetailsDTO;
import top.vnelinpe.management.model.sys.SysUserDO;
import top.vnelinpe.management.util.CommonUtil;
import top.vnelinpe.management.util.JsonUtil;
import top.vnelinpe.management.util.JwtUtil;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.concurrent.TimeUnit;

/**
 * token相关组件
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/19 8:44
 */
@Setter
@Component
public class TokenHandler {
    @Value("${jwt.key.private-key}")
    private String privateKeyStr;

    @Value("${jwt.key.public-key}")
    private String publicKeyStr;

    @Getter
    @Value("${jwt.key.expire-time}")
    private Long expireTime;

    private Algorithm algorithm;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ThreadLocalHolder threadLocalHolder;

    /**
     * 获取当前登录的用户信息
     *
     * @return
     */
    public static UserDetailsDTO getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserDetailsDTO ? (UserDetailsDTO) principal : null;
    }

    @PostConstruct
    private void init() {
        try {
            // 根据配置文件里公私钥字符串创建对应的对象
            PrivateKey privateKey = CommonUtil.getPrivateKey(privateKeyStr);
            PublicKey publicKey = CommonUtil.getPublicKey(publicKeyStr);
            algorithm=Algorithm.ECDSA256((ECPublicKey) publicKey, (ECPrivateKey) privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成一个Token，用公钥
     *
     * @param principal
     * @return
     * @Param key
     */
    public String generateToken(Object principal, String key, String... id) {
        // 序列化
        String tokenStr = JsonUtil.toString(principal);
        // 生成token
        String token = JwtUtil.generate(algorithm, key, tokenStr, expireTime, id);

        return token;
    }

    /**
     * 生成token并考虑单点登录，返回一个加密后的key
     *
     * @return
     */
    public String generateTokenAndSingle() {
        // 获得当前登录的userDetails
        UserDetailsDTO user = getUser();
        // 生成uuid作为userToken的key
        String userSerial = CommonUtil.generateUUID();
        // 设置serial到userDetails里
        user.setSerial(userSerial);
        // 根据userDetails生成token
        String token = generateToken(user, ShareVar.USER_TOKEN_VALUE,userSerial);

        // 加锁
        RLock fairLock = redissonClient.getFairLock(generateUserLockKey(user.getId()));
        try {
            // 设置锁的超时时间
            fairLock.lock(ShareVar.HANDLE_TIME, TimeUnit.SECONDS);
            String userSerialKey = generateUserTokenKey(user.getId());
            // 从redis里获取user的serial
            String userSerialFromRedis = (String)redisTemplate.opsForValue().get(userSerialKey);
            if (userSerialFromRedis != null) {
                // 把旧user的serial添加到黑名单
                signBlackList(userSerialFromRedis);
            }
            // 把当前user的serial设置到redis里
            putKeyValueToRedis(userSerialKey, userSerial);
        } finally {
            fairLock.unlock();
        }

        // 返回token
        return token;
    }

    /**
     * 向redis里存数据
     *
     * @param key
     * @param value
     */
    public void putKeyValueToRedis(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 设置redis里的key过期时间
     *
     * @param key
     * @param expireTime
     */
    public void freshRedisTTL(String key, long... expireTime) {
        if (key != null) {
            redisTemplate.expire(key, expireTime.length == 0 ? this.expireTime : expireTime[0], TimeUnit.SECONDS);
        }
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public Object parseToken(String token, String key) {
        DecodedJWT decodedJWT = JwtUtil.parse(algorithm, token);
        // 记录过期时间到threadLocal里
        threadLocalHolder.set(ShareVar.TOKEN_EXPIRE_TIME_KEY,CommonUtil.asLocalDateTime(decodedJWT.getExpiresAt()));
        return decodedJWT.getClaim(key).asString();
    }

    /**
     * 解析token
     *
     * @param token
     * @param type
     * @param <T>
     * @return
     */
    public <T> T parseToken(String token, String key, Class<T> type) {
        return JsonUtil.parse((String) parseToken(token, key), type);
    }

    /**
     * 判断userKey是否在黑名单里
     *
     * @param key
     * @return
     */
    public boolean isInBlackList(String key) {
        return redisTemplate.opsForValue().get(generateBlackListKey(key)) != null;
    }

    /**
     * 设置token和名单
     *
     * @param key
     */
    public void signBlackList(String key, long... expireTime) {
        redisTemplate.opsForValue().set(generateBlackListKey(key), "null", expireTime.length == 0 ? this.expireTime : expireTime[0], TimeUnit.SECONDS);
    }

    /**
     * 取消黑名单
     *
     * @param key
     */
    public void unsignBlackList(String key) {
        redisTemplate.delete(generateBlackListKey(key));
    }

    /**
     * 生成redis里token黑名单的key
     *
     * @param key
     * @return
     */
    public String generateBlackListKey(String key) {
        return ShareVar.USER_KEY_BLACK + ":" + key;
    }

    /**
     * 记录最近登录的userKey的key
     *
     * @param userId
     * @return
     */
    public String generateUserTokenKey(Long userId) {
        return ShareVar.USER_TOKEN_KEY + ":" + userId;
    }

    /**
     * 生成锁的key
     *
     * @param userId
     * @return
     */
    public String generateUserLockKey(Long userId) {
        return ShareVar.USER_LOCK_KEY + ":" + userId;
    }

    /**
     * 生成验证码的key
     * @param key
     * @return
     */
    public String generateVerifyCodeKey(String key){
        return ShareVar.VERIFY_CODE_KEY+":"+key;
    }

    /**
     * 根据userKey获取user
     *
     * @param token
     * @return
     * @throws NoSuchFieldException
     */
    public UserDetailsDTO getUserByToken(String token) {
        UserDetailsDTO sysUser = parseToken(token, ShareVar.USER_TOKEN_VALUE, UserDetailsDTO.class);
        if(isInBlackList(sysUser.getSerial())){
            throw new TokenExpiredException(null);
        }
        return sysUser;
    }

    /**
     * 注销，把当前token的serial添加到黑名单里
     *
     * @param userSerial
     */
    public void logout(String userSerial) {
        // 把当前user的uuid添加到黑名单里
        signBlackList(userSerial);
    }

    public Object getFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除一个key
     *
     * @param key
     * @return
     */
    public void removeFromRedis(String key) {
        redisTemplate.delete(key);
    }

    public void decreaseTTL(String key) {
        Long expire = redisTemplate.getExpire(key);
        if (expire > 0 && expire > 4) {
            redisTemplate.expire(key, expire / 2, TimeUnit.SECONDS);
        }
    }

}
