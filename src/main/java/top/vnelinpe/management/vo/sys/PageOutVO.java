package top.vnelinpe.management.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 分页查询返回数据模型
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/23 16:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页查询返回数据模型")
public class PageOutVO<T> {
    @ApiModelProperty("总条数")
    private Long totalItems;
    @ApiModelProperty("总页数")
    private Integer totalPages;
    @ApiModelProperty("当前页的元素")
    private List<T> items;
}
