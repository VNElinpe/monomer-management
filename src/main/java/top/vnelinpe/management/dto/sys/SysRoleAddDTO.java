package top.vnelinpe.management.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 18:52
 */
@Data
public class SysRoleAddDTO {
    private Long id;
    private String roleName;
    private String roleDesc;
    private List<Long> authorities;
}
