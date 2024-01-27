package learning.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Geolocation extends BaseModel{
    String latitude;
    String longitude;
}
