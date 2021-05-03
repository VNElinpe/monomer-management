package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 3:16
 */
@Data
@ApiModel("用户修改请求参数模型")
public class SysUserUpdateVO extends SysUserAddVO {
    @NotNull(message = "用户名为空")
    @ApiModelProperty("编号")
    private Long id;
}
