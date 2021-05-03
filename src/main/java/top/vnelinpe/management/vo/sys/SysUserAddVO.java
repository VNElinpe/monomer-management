package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 9:27
 */
@Data
@ApiModel("用户添加参数模型")
public class SysUserAddVO {
    @NotNull(message = "用户名为空")
    @ApiModelProperty("用户名")
    private String username;
    @NotNull(message = "昵称为空")
    @ApiModelProperty("昵称")
    private String surname;
    @NotNull(message = "密码为空")
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("邮箱")
    private String mail;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("角色")
    private List<Long> roles;
    @ApiModelProperty("额外的权限")
    private List<Long> authorities;
}
