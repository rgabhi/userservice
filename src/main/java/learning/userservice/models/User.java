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
    private boolean isEmailVerified;
    private String username;
    private String hashedPassword;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER) // being already mapped by an attribute called user
    private List<UserAddress> userAddresses;
    private String phone;

    private User(UserBuilder userBuilder){
        super();
        this.email = userBuilder.email;
        this.username = userBuilder.username;
        this.hashedPassword = userBuilder.hashedPassword;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.userAddresses = userBuilder.userAddresses;
        this.phone = userBuilder.phone;
    }

    public User() {

    }


    public static class UserBuilder{
        String email;
        boolean isEmailVerified;
        String username;

        List<Role> roles;
        String hashedPassword;
        String firstName;
        String lastName;
        List<UserAddress> userAddresses;
        String phone;

        public  UserBuilder setEmail(String email){
            this.email = email;
            return this;
        }

        public UserBuilder setIsEmailVerified(boolean isEmailVerified){
            this.isEmailVerified =  isEmailVerified;
            return this;
        }

        public UserBuilder setRoles(List<Role> roles) {
            this.roles = roles;
            return this;
        }

        public  UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }
        public  UserBuilder setPassword(String hashedPassword) {
            this.hashedPassword = hashedPassword;
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
