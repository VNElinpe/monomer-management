package top.vnelinpe.management.dto.sys;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.vnelinpe.management.model.sys.SysAuthorityDO;

import java.util.Collection;
import java.util.Set;

/**
 * @author VNElinpe
 * @version 1.0
 * @date 2021/5/2 11:17
 */
@Data
public class UserDetailsDTO implements UserDetails {
    private Long id;
    private String serial;
    private String username;
    private String surname;
    private String password;
    private String phone;
    private String email;
    private Set<SysAuthorityDO> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}