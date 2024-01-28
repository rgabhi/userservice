package learning.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FullName extends BaseModel{
    String firstName;
    String lastName;
}
