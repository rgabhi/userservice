package learning.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class User extends BaseModel{
    String email;
    String username;
    String password;
    @ManyToOne
    FullName fullName;
    @ManyToMany
    List<UserAddress> userAddresses;
    String phone;
    private User(UserBuilder userBuilder){
        super();
        this.email = userBuilder.email;
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.fullName = userBuilder.fullName;
        this.userAddresses = userBuilder.userAddresses;
        this.phone = userBuilder.phone;
    }

    public User() {

    }


    public static class UserBuilder{
        String email;
        String username;
        String password;
        FullName fullName;
        List<UserAddress> userAddresses;
        String phone;

        public  UserBuilder setEmail(String email){
            this.email = email;
            return this;
        }
        public  UserBuilder setUsername(String username){
            this.username = username;
            return this;
        }
        public  UserBuilder setPassword(String password){
            this.password = password;
            return this;
        }

        public  UserBuilder setFullName(FullName name){
            this.fullName = name;
            return  this;
        }
        public UserBuilder setAddresses(List<UserAddress> addresses){
            this.userAddresses = addresses;
            return this;
        }
        public UserBuilder setPhone(String phone){
            this.phone = phone;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }
}
