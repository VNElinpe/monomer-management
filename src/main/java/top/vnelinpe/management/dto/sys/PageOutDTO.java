package top.vnelinpe.management.dto.sys;

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
public class PageOutDTO<T> {
    private Long totalItems;
    private Integer totalPages;
    private List<T> items;
}
