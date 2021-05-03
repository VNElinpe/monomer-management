package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 编号请求参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 23:18
 */
@Data
@ApiModel("编号请求参数模型")
public class LongQuery {
    @NotNull(message = "编号不能为空")
    @ApiModelProperty("编号")
    private Long id;
}
