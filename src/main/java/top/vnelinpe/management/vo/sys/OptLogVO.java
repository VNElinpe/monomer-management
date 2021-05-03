package top.vnelinpe.management.vo.sys;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.vnelinpe.management.core.ExcelLocalDateTimeConverter;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/21 9:34
 */
@Data
@ApiModel("操作日志返回模型")
public class OptLogVO {
    @ApiModelProperty("编号")
    @ExcelProperty("编号")
    private Long id;
    @ApiModelProperty("操作类型")
    @ExcelProperty("操作类型")
    private String optType;
    @ApiModelProperty("操作人姓名")
    @ExcelProperty("操作人姓名")
    private String optUsername;
    @ApiModelProperty("客户端ip地址")
    @ExcelProperty("客户端ip地址")
    private String optClientIp;
    @ApiModelProperty("服务端ip地址")
    @ExcelProperty("服务端ip地址")
    private String optServerIp;
    @ApiModelProperty("客户端agent")
    @ExcelProperty("客户端agent")
    private String optAgent;
    @ApiModelProperty("操作结果")
    @ExcelProperty("操作结果")
    private String optResult;
    @ApiModelProperty("执行时间")
    @ExcelProperty("执行时间")
    private Long optLatency;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("操作时间")
    @ExcelProperty(value = "操作时间", converter = ExcelLocalDateTimeConverter.class)
    private LocalDateTime optTime;
}
