package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户查询参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 21:44
 */
@Data
@ApiModel("用户查询参数模型")
public class SysUserQuery {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("昵称")
    private String surname;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号")
    private String phone;
}
