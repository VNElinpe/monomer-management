package top.vnelinpe.management.util;

import com.alibaba.excel.EasyExcel;

import java.nio.file.Path;
import java.util.List;

/**
 * excel的工具类
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 18:59
 */
public class ExcelUtil {

    /**
     * 写入excel
     *
     * @param path
     * @param data
     * @param aClass
     * @param <T>
     */
    public static <T> void write(String path, List<T> data, Class<T> aClass) {
        CommonUtil.makeDirExists(Path.of(path).getParent().toString());
        EasyExcel.write(path, aClass).sheet().doWrite(data);
    }
}
