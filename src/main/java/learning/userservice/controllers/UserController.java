package learning.userservice.controllers;

import learning.userservice.dtos.AddressDto;
import learning.userservice.dtos.GeolocationDto;
import learning.userservice.dtos.NameDto;
import learning.userservice.dtos.UserDto;
import learning.userservice.models.FullName;
import learning.userservice.models.Geolocation;
import learning.userservice.models.User;
import learning.userservice.models.UserAddress;
import learning.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;
    @Autowired
    public UserController(@Qualifier("selfUserService") UserService userService){
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name="id") Long id){
        return  new ResponseEntity<>(
                convertUserToUserDto(userService.getUser(id)),
                HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : userList){
            userDtos.add(convertUserToUserDto(user));
        }
        return  new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(
                convertUserToUserDto(userService.addUser(convertUserDtoToUSer(userDto))),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name="id") Long id, @RequestBody UserDto userDto){
        return new ResponseEntity<>(
                convertUserToUserDto(userService.updateUser(id, convertUserDtoToUSer(userDto))),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteUser(@PathVariable(name="id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Mapper Methods
    private Geolocation convertGeolocationToGeolocationDto(GeolocationDto geolocationDto){
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(geolocationDto.getLatitude());
        geolocation.setLongitude(geolocationDto.getLongitude());
        return geolocation;
    }

    private GeolocationDto convertGeolocationDtoToGeolocation(Geolocation geolocation){
        GeolocationDto geolocationDto = new GeolocationDto();
        geolocationDto.setLatitude(geolocation.getLatitude());
        geolocationDto.setLongitude(geolocation.getLongitude());
        return geolocationDto;
    }

    private NameDto convertFullNameToNameDto(FullName name){
        NameDto nameDto = new NameDto();
        nameDto.setFirstName(name.getFirstName());
        nameDto.setLastName(name.getLastName());
        return nameDto;
    }

    private FullName convertNameDtoToFullName(NameDto nameDto){
        FullName name = new FullName();
        name.setFirstName(nameDto.getFirstName());
        name.setLastName(nameDto.getLastName());
        return name;
    }



    private UserAddress convertAddressDtoToUserAddress(AddressDto addressDto){
        UserAddress userAddress = new UserAddress();
        userAddress.setCity(addressDto.getCity());
        userAddress.setStreet(addressDto.getStreet());
        userAddress.setNumber(addressDto.getNumber());
        userAddress.setZipcode(addressDto.getZipcode());
        userAddress.setGeolocation(
                convertGeolocationToGeolocationDto(addressDto.getGeolocationDto()));
        return userAddress;
    }

    private AddressDto convertUserAddressToAddressDto(UserAddress address){
        AddressDto addressDto = new AddressDto();
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setNumber(address.getNumber());
        addressDto.setZipcode(address.getZipcode());
        addressDto.setGeolocationDto(
                convertGeolocationDtoToGeolocation(address.getGeolocation()));
        return addressDto;
    }

    private User convertUserDtoToUSer(UserDto userDto){
        List<UserAddress> userAddresses = new ArrayList<>();
        for(AddressDto addressDto : userDto.getAddressDtos()){
            userAddresses.add(convertAddressDtoToUserAddress(addressDto));
        }

        return new User.UserBuilder()
                .setUsername(userDto.getUsername())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword())
                .setAddresses(userAddresses)
                .setFullName(convertNameDtoToFullName(userDto.getNameDto()))
                .setPhone(userDto.getPhone())
                .build();
    }
    private UserDto convertUserToUserDto(User user){
        UserDto userDto = new UserDto();
        List<AddressDto> addressDtos = new ArrayList<>();
        for(UserAddress address : user.getUserAddresses()){
            addressDtos.add(convertUserAddressToAddressDto(address));
        }
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAddressDtos(addressDtos);
        userDto.setNameDto(convertFullNameToNameDto(user.getFullName()));
        userDto.setPhone(user.getPhone());
        return userDto;
    }

}
