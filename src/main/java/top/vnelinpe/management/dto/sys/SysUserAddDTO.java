package top.vnelinpe.management.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.vnelinpe.management.vo.sys.SysUserAddVO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 10:47
 */
@Data
public class SysUserAddDTO {
    private Long id;
    private String username;
    private String surname;
    private String password;
    private String email;
    private String phone;
    private List<Long> roles;
    private List<Long> authorities;
}
