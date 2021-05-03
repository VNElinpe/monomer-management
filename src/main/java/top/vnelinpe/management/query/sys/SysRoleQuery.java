package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色查询参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 21:44
 */
@Data
@ApiModel("角色查询参数模型")
public class SysRoleQuery {
    @ApiModelProperty("角色名")
    private String roleName;
    @ApiModelProperty("角色描述")
    private String roleDesc;
}
