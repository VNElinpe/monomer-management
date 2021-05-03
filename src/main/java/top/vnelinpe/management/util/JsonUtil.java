package top.vnelinpe.management.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 处理json的工具类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 13:40
 */
public class JsonUtil {
    /**
     * 从输入流里反序列化
     *
     * @param inputStream
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parse(InputStream inputStream, Class<T> type) throws IOException {
        return JSON.parseObject(inputStream, type);
    }

    /**
     * 字符串 ===> 实体类
     *
     * @param source
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parse(String source, Class<T> type) {
        return JSON.parseObject(source, type);
    }

    /**
     * 实体类 ==> 字符串
     *
     * @param o
     * @return
     */
    public static String toString(Object o) {
        return JSON.toJSONString(o, SerializerFeature.IgnoreNonFieldGetter);
    }

    /**
     * 以json的方式写返回消息
     *
     * @param response
     */
    public static void writeResponse(HttpServletResponse response, Object data) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(JSON.toJSONBytes(data));
        } catch (IOException e) {
            throw e;
        }
    }
}
