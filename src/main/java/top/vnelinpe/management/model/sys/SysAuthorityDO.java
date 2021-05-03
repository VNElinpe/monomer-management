package top.vnelinpe.management.model.sys;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 9:28
 */
@Data
public class SysAuthorityDO implements GrantedAuthority {
    private Long id;
    private String authorityName;
    private String authorityDesc;

    @Override
    public String getAuthority() {
        return authorityName;
    }
}
