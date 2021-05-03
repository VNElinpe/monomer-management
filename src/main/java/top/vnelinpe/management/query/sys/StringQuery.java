package top.vnelinpe.management.query.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 单个字符串请求参数模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/26 11:09
 */
@Data
@ApiModel("单个字符串请求参数模型")
public class StringQuery {
    @NotNull(message = "输入字符串不能为空")
    @ApiModelProperty("字符串")
    private String str;
}
