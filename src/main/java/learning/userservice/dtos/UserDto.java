package learning.userservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;

@Getter
@Setter
public class UserDto {
        Long userId;
        String email;
        String username;
        String password;
        NameDto nameDto;
        AddressDto  addressDto;
        String phone;
}
