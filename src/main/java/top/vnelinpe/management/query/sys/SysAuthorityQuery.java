package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限查询参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/25 21:44
 */
@Data
@ApiModel("权限查询参数模型")
public class SysAuthorityQuery {
    @ApiModelProperty("权限名")
    private String authorityName;
    @ApiModelProperty("权限描述")
    private String authorityDesc;
}
