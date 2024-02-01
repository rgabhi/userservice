package learning.userservice.controllers;

import learning.userservice.dtos.AddressDto;
import learning.userservice.dtos.GeolocationDto;
import learning.userservice.dtos.NameDto;
import learning.userservice.dtos.UserDto;
import learning.userservice.exceptions.EmptyRequiredFieldException;
import learning.userservice.exceptions.UserAlreadyExistsException;
import learning.userservice.exceptions.UserNotFoundException;
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
    public ResponseEntity<UserDto> getUser(@PathVariable(name="id") Long id) throws UserNotFoundException {
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
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) throws UserAlreadyExistsException, EmptyRequiredFieldException {
        return new ResponseEntity<>(
                convertUserToUserDto(userService.addUser(convertUserDtoToUSer(userDto))),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name="id") Long id, @RequestBody UserDto userDto) throws EmptyRequiredFieldException, UserNotFoundException {
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
    private UserAddress convertAddressDtoToUserAddress(AddressDto addressDto){
        UserAddress userAddress = new UserAddress();
        userAddress.setCity(addressDto.getCity());
        userAddress.setStreet(addressDto.getStreet());
        userAddress.setNumber(addressDto.getNumber());
        userAddress.setZipcode(addressDto.getZipcode());
        userAddress.setLongitude(addressDto.getGeolocation().getLongitude());
        userAddress.setLatitude(addressDto.getGeolocation().getLatitude());
        return userAddress;
    }

    private AddressDto convertUserAddressToAddressDto(UserAddress address){
        AddressDto addressDto = new AddressDto();
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setNumber(address.getNumber());
        addressDto.setZipcode(address.getZipcode());
        GeolocationDto geolocationDto = new GeolocationDto();
        geolocationDto.setLatitude(address.getLatitude());
        geolocationDto.setLongitude(address.getLongitude());
        addressDto.setGeolocation(geolocationDto);
        return addressDto;
    }

    private User convertUserDtoToUSer(UserDto userDto) throws EmptyRequiredFieldException {
        List<UserAddress> userAddresses = new ArrayList<>();
        for(AddressDto addressDto : userDto.getAddress()){
            userAddresses.add(convertAddressDtoToUserAddress(addressDto));
        }
        return new User.UserBuilder()
                .setUsername(userDto.getUsername())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword())
                .setAddresses(userAddresses)
                .setFirstName(userDto.getName().getFirstname())
                .setLastName(userDto.getName().getLastname())
                .setPhone(userDto.getPhone())
                .build();
    }
    private UserDto convertUserToUserDto(User user){

        UserDto userDto = new UserDto();
        // addressDtos
        List<AddressDto> addressDtos = new ArrayList<>();
        for(UserAddress address : user.getUserAddresses()){
            addressDtos.add(convertUserAddressToAddressDto(address));
        }
        userDto.setUserId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAddress(addressDtos);
        //nameDto
        NameDto nameDto = new NameDto();
        nameDto.setFirstname(user.getFirstName());
        nameDto.setLastname(user.getLastName());
        userDto.setName(nameDto);
        userDto.setPhone(user.getPhone());
        return userDto;
    }

}
