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
    Integer number;
    String zipcode;
    @ManyToOne
    Geolocation geolocation;
}
