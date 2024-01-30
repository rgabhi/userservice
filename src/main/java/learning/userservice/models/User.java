package learning.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import learning.userservice.exceptions.EmptyRequiredFieldException;
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
    String firstName;
    String lastName;
    @OneToMany
    List<UserAddress> userAddresses;
    String phone;
    private User(UserBuilder userBuilder){
        super();
        this.email = userBuilder.email;
        this.username = userBuilder.username;
        this.password = userBuilder.password;
//        this.fullName = userBuilder.fullName;
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
//        FullName fullName;
        String firstName;
        String lastName;
        List<UserAddress> userAddresses;
        String phone;

        public  UserBuilder setEmail(String email) throws EmptyRequiredFieldException {
            if(email == null){
                throw new EmptyRequiredFieldException("email can't be null.");
            }
            this.email = email;
            return this;
        }
        public  UserBuilder setUsername(String username) throws EmptyRequiredFieldException {
            if(username == null){
                throw new EmptyRequiredFieldException("username can't be null.");
            }
            this.username = username;
            return this;
        }
        public  UserBuilder setPassword(String password) throws EmptyRequiredFieldException {
            if(password == null || password.isEmpty()){
                throw new EmptyRequiredFieldException("password can't be null/empty.");
            }
            this.password = password;
            return this;
        }

//        public  UserBuilder setFullName(FullName name){
//            this.fullName = name;
//            return  this;
//        }
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
