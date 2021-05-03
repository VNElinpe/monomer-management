package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 运行时信息模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 8:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("运行时信息模型")
public class RuntimeVO {
    @ApiModelProperty("虚拟机名称")
    private String name;
    @ApiModelProperty("虚拟机供应商")
    private String vendor;
    @ApiModelProperty("虚拟机版本")
    private String version;
    @ApiModelProperty("开启时间")
    private String startTime;
    @ApiModelProperty("运行时长")
    private String upTime;
}
