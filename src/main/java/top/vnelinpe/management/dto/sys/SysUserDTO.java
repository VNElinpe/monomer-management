package top.vnelinpe.management.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.vnelinpe.management.vo.sys.SysUserVO;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 11:00
 */
@Data
public class SysUserDTO {
    private Long id;
    private String username;
    private String surname;
    private String email;
    private String phone;
}
