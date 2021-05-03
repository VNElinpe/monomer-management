package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户分页查询参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 2:41
 */
@Data
@ApiModel("用户分页查询参数模型")
public class SysUserPageQuery extends SysUserQuery {
    @Valid
    @ApiModelProperty("分页信息")
    private PageQuery page;
}
