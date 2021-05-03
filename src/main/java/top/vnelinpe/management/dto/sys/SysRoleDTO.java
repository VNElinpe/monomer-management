package top.vnelinpe.management.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 18:25
 */
@Data
public class SysRoleDTO {
    private Long id;
    private String roleName;
    private String roleDesc;
}
