package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作系统信息模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 3:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("操作系统信息模型")
public class OperationSystemVO {
    @ApiModelProperty("体系结构")
    private String arch;
    @ApiModelProperty("处理器个数")
    private Integer availableProcessors;
    @ApiModelProperty("操作系统名称")
    private String name;
    @ApiModelProperty("负载因子")
    private Double systemLoadAverage;
    @ApiModelProperty("版本号")
    private String version;

}
