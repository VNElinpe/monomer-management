package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 18:38
 */
@Data
@ApiModel("日志导出请求参数模型")
public class OptLogQuery {
    @ApiModelProperty("操作类型")
    private String optType;
    @ApiModelProperty("操作人员名字")
    private String optUsername;
    @ApiModelProperty("客户端ip")
    private String optClientIp;
    @ApiModelProperty("服务端ip")
    private String optServerIp;
    @ApiModelProperty("客户端agent")
    private String optAgent;
    @ApiModelProperty("返回结果")
    private String optResult;
    @ApiModelProperty("延时下限")
    private String startOptLatency;
    @ApiModelProperty("延时上限")
    private String endOptLatency;
    @ApiModelProperty("日志开始时间")
    @Pattern(regexp = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})|^$", message = "日志开始时间格式有误")
    private String startOptTime;
    @ApiModelProperty("日志截止时间")
    @Pattern(regexp = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})|^$", message = "日志截止时间格式有误")
    private String endOptTime;
}
