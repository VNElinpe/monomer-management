package top.vnelinpe.management.model.sys;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * TODO
 *
 * @author VNElinpe
 * @version 1.0
 * @date 2020/10/12 9:27
 */
@Data
public class SysUserDO {
    private Long id;
    private String username;
    private String surname;
    private String password;
    private String phone;
    private String email;
}
