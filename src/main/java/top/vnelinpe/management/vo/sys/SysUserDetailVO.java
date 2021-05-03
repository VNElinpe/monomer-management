package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.vnelinpe.management.model.sys.SysAuthorityDO;

import java.util.Set;

/**
 * 用户详情模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 23:06
 */
@Data
@ApiModel("用户详情模型")
public class SysUserDetailVO extends SysUserVO {
    @ApiModelProperty("用户角色")
    private Set<SysRoleVO> roles;
    @ApiModelProperty("用户额外的权限")
    private Set<SysAuthorityDO> authorities;
}
