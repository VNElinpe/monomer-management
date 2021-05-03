package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 权限添加参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 9:26
 */
@Data
@ApiModel("权限添加参数模型")
public class SysAuthorityAddVO {
    @NotNull(message = "权限名为空")
    @ApiModelProperty("权限名")
    private String authorityName;
    @ApiModelProperty("权限描述")
    private String authorityDesc;
}
