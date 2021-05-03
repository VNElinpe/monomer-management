package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分页查询的输入数据
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 14:15
 */
@Data
@ApiModel("分页参数模型")
public class PageQuery {
    @NotNull(message = "当前页数不能为空")
    @ApiModelProperty(value = "当前页数", required = true)
    private Integer currentPage;

    @NotNull(message = "每页条数不能为空")
    @ApiModelProperty(value = "每页条数", required = true)
    private Integer pageSize;
}
