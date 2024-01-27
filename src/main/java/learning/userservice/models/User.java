package learning.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    String email;
    String username;
    String password;
    UserName userName;
    UserAddress userAddress;
    String phone;
}
