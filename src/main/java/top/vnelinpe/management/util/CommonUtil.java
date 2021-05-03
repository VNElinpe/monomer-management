package top.vnelinpe.management.util;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用的工具类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 16:31
 */
public class CommonUtil {
    public static final Pattern pattern = Pattern.compile("(0:){2,}");

    /**
     * 生成UUID
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 获取RSA的公钥和私钥对
     *
     * @return
     */
    public static Map<String, String> generateRSAPairKey() throws NoSuchAlgorithmException {
//        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.ES256);
        KeyPairGenerator ec = KeyPairGenerator.getInstance("EC");
        ec.initialize(256);
        KeyPair keyPair = ec.generateKeyPair();
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        Map<String, String> map = new HashMap<>();
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }

    /**
     * 获取私钥
     *
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 压缩ip地址
     *
     * @param ip
     * @return
     */
    public static byte[] compressIP(String ip) {
        return isIPV4(ip) ? compressIPV4(ip) : compressIPV6(ip);
    }

    /**
     * 解压ip地址
     *
     * @param data
     * @return
     */
    public static String decompressIP(byte[] data) {
        return data.length == 4 ? decompressIPV4(data) : decompressIPV6(data);
    }

    public static String decompressIP(Byte[] data) {
        if (data == null) {
            return null;
        }
        byte[] datab = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            datab[i] = data[i].byteValue();
        }
        return decompressIP(datab);
    }

    /**
     * 压缩ipv4
     *
     * @param ip
     * @return
     */
    public static byte[] compressIPV4(String ip) {
        String[] split = ip.split("\\.");
        byte[] result = new byte[split.length];
        for (int i = 0; i < split.length; i++) {
            result[i] = (byte) Integer.parseInt(split[i]);
        }
        return result;
    }

    /**
     * 压缩ipv6
     *
     * @param ip
     * @return
     */
    public static byte[] compressIPV6(String ip) {
        byte[] result = new byte[16];
        int left = 0;
        int right = 15;
        // 有简写
        if (ip.contains("::")) {
            String[] split = ip.split("::");
            // 处理左边
            if (!split[0].isEmpty()) {
                String[] splitLeft = split[0].split(":");
                for (int i = 0; i < splitLeft.length; i++) {
                    int value = Integer.parseInt(splitLeft[i], 16);
                    result[left++] = (byte) (value >> 8);
                    result[left++] = (byte) value;
                }
            }
            // 处理右边
            if (!split[1].isEmpty()) {
                String[] splitRight = split[1].split(":");
                for (int i = splitRight.length - 1; i >= 0; i--) {
                    int value = Integer.parseInt(splitRight[i], 16);
                    result[right--] = (byte) value;
                    result[right--] = (byte) (value >> 8);
                }
            }
            // 填充中间
            while (left < right) {
                result[left++] = 0;
            }
        } else {
            String[] split = ip.split(":");
            for (int i = 0; i < split.length; i++) {
                int value = Integer.parseInt(split[i], 16);
                result[left++] = (byte) (value >> 8);
                result[left++] = (byte) value;
            }
        }
        return result;
    }

    /**
     * 解压ipv4
     *
     * @param data
     * @return
     */
    public static String decompressIPV4(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            stringBuilder.append(toIntUnsigned(data[i])).append(".");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    /**
     * 解压ipv6
     *
     * @param data
     * @return
     */
    public static String decompressIPV6(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String high = Integer.toHexString(toIntUnsigned(data[i++]));
            if (!high.equals("0")) {
                stringBuilder.append(high);
            }
            String low = Integer.toHexString(toIntUnsigned(data[i]));
            if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) != ':' && low.length() == 1) {
                low = "0" + low;
            }
            stringBuilder.append(low).append(":");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        String ip = stringBuilder.toString();
        //找到最长的连续的0
        Matcher matcher = pattern.matcher(ip);
        String maxStr = "";
        int start = -1;
        int end = -1;
        while (matcher.find()) {
            if (maxStr.length() < matcher.group().length()) {
                maxStr = matcher.group();
                start = matcher.start();
                end = matcher.end();
            }
        }
        if (start != -1) {
            ip = ip.substring(0, start) + ":" + ip.substring(end);
        }
        if (ip.startsWith(":")) {
            ip = ":" + ip;
        }
        return ip;
    }


    /**
     * 判断ip地址是不是ipv4
     *
     * @param ip
     * @return
     */
    public static boolean isIPV4(String ip) {
        return ip.contains(".");
    }

    public static int toIntUnsigned(byte data) {
        return ((int) data) << 24 >>> 24;
    }


    /**
     * 获取当前的时间字符串
     *
     * @return
     */
    public static String getLocalDateTimeString(LocalDateTime... localDateTimes) {
        LocalDateTime localDateTime = localDateTimes.length == 0 ? LocalDateTime.now() : localDateTimes[0];
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 返回 yyyy-MM-dd HH:mm:ss
     *
     * @param milliSecond
     * @return
     */
    public static String milliSecondtoDateString(long milliSecond) {
        if (milliSecond < 0) {
            return null;
        }
        int ss = milliSecond > 1000 ? (int) (milliSecond / 1000) : 0;
        int mm = ss >= 60 ? ss / 60 : 0;
        int HH = mm >= 60 ? mm / 60 : 0;
        int dd = HH >= 24 ? HH / 24 : 0;
        int MM = dd >= 30 ? dd / 30 : 0;
        int yyyy = MM >= 12 ? MM / 12 : 0;
        StringBuilder sb = new StringBuilder();
        return sb.append(String.format("%04d", yyyy)).append("-")
                .append(String.format("%02d", MM >= 12 ? MM % 12 : MM)).append("-")
                .append(String.format("%02d", dd >= 30 ? dd % 30 : dd)).append(" ")
                .append(String.format("%02d", HH >= 24 ? HH % 24 : HH)).append(":")
                .append(String.format("%02d", mm >= 60 ? mm % 60 : mm)).append(":")
                .append(String.format("%02d", ss >= 60 ? ss % 60 : ss)).toString();
    }


    /**
     * 保证路径存在
     *
     * @param path
     */
    public static void makeDirExists(String path) {
        if (StringUtils.isEmpty(path)) {
            return;
        }
        Path of = Path.of(path);
        if (!Files.exists(of)) {
            try {
                Files.createDirectories(of);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
