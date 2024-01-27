package learning.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserAddress extends BaseModel{
    String city;
    String street;
    String number;
    String zipcode;
    @ManyToOne
    Geolocation geolocation;
}
