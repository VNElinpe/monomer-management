package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类的加载情况
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 2:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("类的加载情况模型")
public class ClassLoadStatusVO {
    @ApiModelProperty("已经加载的类个数")
    private Integer loadedClassCount;
    @ApiModelProperty("总共加载的类个数")
    private Long totalLoadedClassCount;
    @ApiModelProperty("没有被加载的类个数")
    private Long unloadedClassCount;
}
