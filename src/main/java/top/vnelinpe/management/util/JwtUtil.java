package top.vnelinpe.management.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 与jwt相关的工具类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 16:24
 */
public class JwtUtil {

    /**
     * 生成jwt
     *
     * @param algorithm
     * @param key    键
     * @param value  值
     * @param second 有效期是多少秒
     * @return
     */
    public static String generate(Algorithm algorithm, String key, String value, Long second, String... id) {
        String serial=id.length>0?id[0]:CommonUtil.generateUUID();
        return JWT.create()
                .withJWTId(serial)
                .withExpiresAt(CommonUtil.asDate(LocalDateTime.now().plusSeconds(second)))
                .withClaim(key,value)
                .sign(algorithm);
//        return Jwts.builder()
//                .claim(key, value)
//                .setId(uuid)
//                .setExpiration(CommonUtil.asDate(LocalDateTime.now().plusSeconds(second)))
//                .signWith(seKey, SignatureAlgorithm.ES256)
//                .compact();
    }

    /**
     * 解析jwt
     *
     * @param algorithm
     * @param token jwt令牌
     * @param key   键
     * @return
     */
    public static Object parse(Algorithm algorithm, String token, String key) {
        return parse(algorithm,token).getClaims().get(key);
    }

    /**
     * 解析token，包含token之外的其他数据
     *
     * @param algorithm
     * @param token
     * @return
     */
    public static DecodedJWT parse(Algorithm algorithm, String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token);

//        Claims o = null;
//        try {
//
//            JWT.require()
//                    .build().verify(token)
//
//            o = Jwts.parserBuilder()
//                    .setSigningKey(seKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            if (o == null) {
//                throw new NullPointerException();
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//        return o;
    }

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
//        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS512);
////        String privateKey = "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";
////        String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAihQRR/7kklx+tMbqlEtUQwoetsRcXRyZx+OSshkk2f90WKsQx3EYliWJWQOJ7uHAbeRZirLNHvQ/8RnakM4KntC9+T1TZHQLdn1nQIOxYNLPq1ICaYTgYQMWyrdpe73vCQiVfMzl45IPSx6hbcfInStlvsk/n+ZhjeZy6TntFM3Zw0bRJR01ZygiG8BIBErq/ZkZzBwgt4NGr1YVcYBDT5ZnYSP+s7iqBS25BtMnSYT3xvedb+lAZ+LdnfZ7d8b+HVBj9vZFdrd4kCOEswAYpQQJejVGOIYV0OshIYJZSKdpKtrvDjpb0u2MYIYM05NXMo56E6D3v5a9+wD4TUzDctlIyX8tTpo4vX+rdVBMC8CV4zi/74Q6HsXfuoEYaHSdif2S+s19DLNr4SsEohGAtVuh1bPmWaBSsay3wwISkMsrVkldHXNQUzGNPh4BWaxjaS13JR9Mh2OyFDIP/40fVu+1j8HdXxALngrDLFiZJE8n7VXOBoYrErWKeBg9mijvUeBfkpyOsE9BhnYMg2S6ssrm9kYAPnbXZZbbCq1PS43x9bfqzl4uVjq8gHzueiEPFenOn61P61Gwv1IxLIxjyrExkO93JnDYC7/nN1MLIg1iVQfPVYYEckaLUwx8O0Rgz0/vUpMB79+KUNN7rJaYdw2Y2KoN8UUupYy6YMcL5jUCAwEAAQ==";
//        Map<String, String> stringStringMap = CommonUtil.generateRSAPairKey();
//        String privateKey = stringStringMap.get("privateKey");
//        String publicKey = stringStringMap.get("publicKey");
//        System.out.println(privateKey);
//        System.out.println(publicKey);
//        PrivateKey aPrivate = CommonUtil.getPrivateKey(privateKey);
//        PublicKey aPublic = CommonUtil.getPublicKey(publicKey);
//        String generate = generate(aPrivate, "user", "vnelinpe", 1000L);
//        System.out.println(generate);
//        System.out.println(parse(aPublic, generate));

        final Map<String, String> stringStringMap = CommonUtil.generateRSAPairKey();
        final PrivateKey privateKey = CommonUtil.getPrivateKey(stringStringMap.get("privateKey"));
        final PublicKey publicKey = CommonUtil.getPublicKey(stringStringMap.get("publicKey"));

        ECPublicKey ecPublicKey=(ECPublicKey) publicKey;
        System.out.println(ecPublicKey.getW());

    }
}
