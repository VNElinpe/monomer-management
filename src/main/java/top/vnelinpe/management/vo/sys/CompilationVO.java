package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 即时编译信息模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 2:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("即时编译信息模型")
public class CompilationVO {
    @ApiModelProperty("编译器名称")
    private String name;
    @ApiModelProperty("总共编译时间")
    private Long totalCompilationTime;
}
