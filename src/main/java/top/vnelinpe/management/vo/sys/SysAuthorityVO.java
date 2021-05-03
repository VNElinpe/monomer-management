package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 权限
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 21:19
 */
@Data
@ApiModel("权限模型")
public class SysAuthorityVO {
    @NotNull(message = "权限编号为空")
    @ApiModelProperty("编号")
    private Long id;
    @NotNull(message = "权限名为空")
    @ApiModelProperty("权限名")
    private String authorityName;
    @ApiModelProperty("权限描述")
    private String authorityDesc;
}
