package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 线程信息模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 10:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("线程信息模型")
public class ThreadVO {
    @ApiModelProperty("创建的线程总数")
    private Long totalStartedCount;
    @ApiModelProperty("守护线程数")
    private Integer deamonCount;
    @ApiModelProperty("线程总数")
    private Integer totalCount;
    @ApiModelProperty("峰值线程数")
    private Integer peakCount;

}
