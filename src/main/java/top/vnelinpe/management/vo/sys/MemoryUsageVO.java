package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内存使用信息
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 2:59
 */
@Data
@ApiModel("内存使用模型")
public class MemoryUsageVO {
    @ApiModelProperty("堆内存")
    private Memory heap = new Memory();
    @ApiModelProperty("非堆内存")
    private Memory nonHeap = new Memory();

    @Data
    @ApiModel("内存模型")
    public static class Memory {
        @ApiModelProperty("初始内存")
        private Long init;
        @ApiModelProperty("已使用")
        private Long used;
        @ApiModelProperty("可使用")
        private Long committed;
        @ApiModelProperty("最大内存")
        private Long max;
    }
}
