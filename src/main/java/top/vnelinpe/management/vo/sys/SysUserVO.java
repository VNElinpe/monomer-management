package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 21:19
 */
@Data
@ApiModel("用户模型")
public class SysUserVO {
    @ApiModelProperty("编号")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("昵称")
    private String surname;
    @ApiModelProperty("邮箱")
    private String mail;
    @ApiModelProperty("邮箱")
    private String phone;
}
