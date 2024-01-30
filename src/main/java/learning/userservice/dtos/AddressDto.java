package learning.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    String city;
    String street;
    Integer number;
    String zipcode;
    GeolocationDto geolocation;
}
