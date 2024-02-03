package learning.userservice.models;

import jakarta.persistence.*;
import learning.userservice.exceptions.EmptyRequiredFieldException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseModel{
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER) // being already mapped by an attribute called user
    private List<UserAddress> userAddresses;
    private String phone;
    private User(UserBuilder userBuilder){
        super();
        this.email = userBuilder.email;
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.userAddresses = userBuilder.userAddresses;
        this.phone = userBuilder.phone;
    }

    public User() {

    }


    public static class UserBuilder{
        String email;
        String username;
        String password;
        String firstName;
        String lastName;
        List<UserAddress> userAddresses;
        String phone;

        public  UserBuilder setEmail(String email){
            this.email = email;
            return this;
        }
        public  UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }
        public  UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public  UserBuilder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public UserBuilder setLastName(String lastName){
            this.lastName = lastName;
            return this;
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
