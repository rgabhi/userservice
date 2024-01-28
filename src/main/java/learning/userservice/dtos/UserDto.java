package learning.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
        Long userId;
        String email;
        String username;
        String password;
        NameDto nameDto;
        List<AddressDto> addressDtos;
        String phone;
}
