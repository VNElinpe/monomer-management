package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 9:26
 */
@Data
@ApiModel("角色添加参数模型")
public class SysRoleAddVO {
    @NotNull(message = "角色名为空")
    @ApiModelProperty("角色名")
    private String roleName;
    @ApiModelProperty("角色描述")
    private String roleDesc;
    @ApiModelProperty("权限列表")
    private List<Long> authorities;
}
