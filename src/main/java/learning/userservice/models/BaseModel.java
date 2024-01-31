package learning.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@MappedSuperclass
public class BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    Date createdAt;
    Date updatedAt;
    boolean isDeleted;
}
