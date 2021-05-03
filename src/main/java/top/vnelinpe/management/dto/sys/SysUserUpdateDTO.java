package top.vnelinpe.management.dto.sys;

import lombok.Data;
import top.vnelinpe.management.vo.sys.SysUserUpdateVO;

/**
 * service层更新user的入参
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 10:45
 */
@Data
public class SysUserUpdateDTO extends SysUserAddDTO {
    private Long id;
}
