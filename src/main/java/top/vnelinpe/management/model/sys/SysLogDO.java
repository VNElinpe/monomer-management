package top.vnelinpe.management.model.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/21 9:44
 */
@Data
public class SysLogDO {
    private Long id;
    private String optType;
    private String optUsername;
    private String optClientIp;
    private String optServerIp;
    private String optAgent;
    private String optResult;
    private Long optLatency;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime optTime;
}
