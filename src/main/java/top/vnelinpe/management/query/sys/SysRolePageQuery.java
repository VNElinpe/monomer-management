package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 角色分页查询参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 2:41
 */
@Data
@ApiModel("角色分页查询参数模型")
public class SysRolePageQuery extends SysRoleQuery {
    @Valid
    @NotNull(message = "分页信息不能为空")
    @ApiModelProperty("分页信息")
    private PageQuery page;
}
