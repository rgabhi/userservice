package learning.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name="address")
public class UserAddress extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String city;
    private String street;
    private Integer number;
    private String zipcode;
    private String longitude;
    private String latitude;
}
