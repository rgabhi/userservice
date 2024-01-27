package learning.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    String email;
    String username;
    String password;
    @ManyToOne
    UserName userName;
    @ManyToMany
    List<UserAddress> userAddresses;
    String phone;
}
