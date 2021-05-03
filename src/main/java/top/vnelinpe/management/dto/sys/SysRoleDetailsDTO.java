package top.vnelinpe.management.dto.sys;

import lombok.Data;
import top.vnelinpe.management.model.sys.SysAuthorityDO;

import java.util.List;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 18:29
 */
@Data
public class SysRoleDetailsDTO extends SysRoleDTO{
    private List<SysAuthorityDO> authorities;
}
