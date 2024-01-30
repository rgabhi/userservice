package learning.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="address")
public class UserAddress extends BaseModel{
    @ManyToOne
    User user;
    String city;
    String street;
    Integer number;
    String zipcode;
    String longitude;
    String latitude;
}
