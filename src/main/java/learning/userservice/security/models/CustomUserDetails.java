package learning.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import learning.userservice.models.Role;
import learning.userservice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@JsonDeserialize
public class CustomUserDetails implements UserDetails {
//    private User user;
    private Collection<GrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;



    private long userId;
    public CustomUserDetails(){};
    public CustomUserDetails(User user){
//        this.user = user;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.username = user.getEmail();
        this.password = user.getHashedPassword();
        this.authorities = new ArrayList<>();
        this.userId= user.getId();
        for(Role role : user.getRoles()){
            this.authorities.add(new CustomGrantedAuthority(role));
        }

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for(Role role : user.getRoles()){
//            grantedAuthorities.add(new CustomGrantedAuthority(role));
//        }
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public long getUserId() {
        return userId;
    }
}
