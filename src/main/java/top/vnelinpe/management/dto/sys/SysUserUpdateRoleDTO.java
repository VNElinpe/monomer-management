package top.vnelinpe.management.dto.sys;

import lombok.Data;
import top.vnelinpe.management.vo.sys.SysUserUpdateRoleVO;
import top.vnelinpe.management.vo.sys.SysUserUpdateVO;

import java.util.List;

/**
 * service层更新用户角色权限信息的入参
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 10:46
 */
@Data
public class SysUserUpdateRoleDTO {
    private Long id;
    private List<Long> roles;
    private List<Long> authorities;
}
