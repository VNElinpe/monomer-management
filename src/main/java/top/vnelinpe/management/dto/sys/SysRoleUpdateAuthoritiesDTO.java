package top.vnelinpe.management.dto.sys;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 18:31
 */
@Data
public class SysRoleUpdateAuthoritiesDTO {
    private Long id;
    private List<Long> authorities;
}
