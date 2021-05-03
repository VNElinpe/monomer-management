package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户更新角色输入参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 8:45
 */
@Data
@ApiModel("用户更新角色输入参数模型")
public class SysUserUpdateRoleVO {
    @NotNull(message = "用户id为空")
    private Long id;
    private List<Long> roles;
    private List<Long> authorities;
}
