package top.vnelinpe.management.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.vnelinpe.management.model.sys.SysAuthorityDO;
import top.vnelinpe.management.vo.sys.SysRoleVO;
import top.vnelinpe.management.vo.sys.SysUserDetailVO;

import java.util.Set;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 11:05
 */
@Data
public class SysUserDetailsDTO extends SysUserDTO{
    private Set<SysRoleVO> roles;
    private Set<SysAuthorityDO> authorities;
}
