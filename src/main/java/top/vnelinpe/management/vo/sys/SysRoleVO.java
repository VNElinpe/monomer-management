package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 角色模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 21:19
 */
@Data
@ApiModel("角色模型")
public class SysRoleVO {
    @NotNull(message = "角色编号为空")
    @ApiModelProperty("编号")
    private Long id;
    @NotNull(message = "角色名为空")
    @ApiModelProperty("角色名")
    private String roleName;
    @ApiModelProperty("角色描述")
    private String roleDesc;
}
