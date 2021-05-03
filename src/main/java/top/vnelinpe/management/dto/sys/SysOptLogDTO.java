package top.vnelinpe.management.dto.sys;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.vnelinpe.management.core.ExcelLocalDateTimeConverter;

import java.time.LocalDateTime;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 19:24
 */
@Data
public class SysOptLogDTO {
    private Long id;
    private String optType;
    private String optUsername;
    private String optClientIp;
    private String optServerIp;
    private String optAgent;
    private String optResult;
    private Long optLatency;
    private LocalDateTime optTime;
}
