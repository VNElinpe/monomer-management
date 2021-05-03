package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录所需要的数据
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 13:45
 */
@Data
@ApiModel("登录输入参数模型")
public class LoginVO {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
