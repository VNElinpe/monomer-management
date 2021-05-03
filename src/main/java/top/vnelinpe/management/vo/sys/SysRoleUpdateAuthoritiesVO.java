package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 8:47
 */
@Data
@ApiModel("角色更新权限输入参数模型")
public class SysRoleUpdateAuthoritiesVO {
    @NotNull(message = "角色id为空")
    private Long id;
    @NotNull(message = "权限id列表为空")
    private List<Long> authorities;
}
