package learning.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import learning.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;
@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
//    private Role role;
    public CustomGrantedAuthority(){};
    private String authority;

    public CustomGrantedAuthority(Role role){
//        this.role = role;
        this.authority = role.getName();
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
