package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.vnelinpe.management.model.sys.SysAuthorityDO;

import java.util.List;

/**
 * 角色详情模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 23:08
 */
@Data
@ApiModel("角色详情模型")
public class SysRoleDetailVO extends SysRoleVO {
    @ApiModelProperty("角色权限")
    private List<SysAuthorityDO> authorities;
}
