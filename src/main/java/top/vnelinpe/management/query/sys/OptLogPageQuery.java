package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * 日志相关请求参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 14:19
 */
@Data
@ApiModel("日志查询请求参数模型")
public class OptLogPageQuery extends OptLogQuery {
    @Valid
    @ApiModelProperty("分页信息")
    private PageQuery page;
}
