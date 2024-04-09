package learning.userservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import learning.userservice.dtos.*;
import learning.userservice.exceptions.*;
import learning.userservice.models.Token;
import learning.userservice.models.User;
import learning.userservice.models.UserAddress;
import learning.userservice.services.UserService;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
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

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequestDto requestDto) throws UserAlreadyExistsException, JsonProcessingException {
        User user = userService.signup(requestDto.getFirstName(), requestDto.getLastName(),
                requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto requestDto) throws UserNotFoundException, InvalidFieldException {
        // check if user and password in db
        // if yes return user
        // else throw error
        return userService.login(requestDto.getEmail(), requestDto.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto) throws TokenNotFoundOrExpiredException {
        // delete token if exists
        // otherwise throw 404 error
        userService.logout(requestDto.getToken());
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public User validateToken(@PathVariable("token") @NonNull String token) throws TokenNotFoundOrExpiredException {
        return userService.validateToken(token);
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
    public ResponseEntity<UserDto> updateUser(@PathVariable(name="id") Long id, @RequestBody UserDto userDto) throws UserNotFoundException {
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
        addressDto.setAddressId(address.getId());
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

    private User convertUserDtoToUSer(UserDto userDto){
        // address
        List<UserAddress> userAddresses = new ArrayList<>();
        if(userDto.getAddress() != null){
            for(AddressDto addressDto : userDto.getAddress()){
                userAddresses.add(convertAddressDtoToUserAddress(addressDto));
            }
        }
        else{
            userAddresses = null;
        }
        // name dto
        NameDto nameDto = new NameDto();
        if(userDto.getName() != null){
            nameDto.setFirstname(userDto.getName().getFirstname());
            nameDto.setLastname(userDto.getName().getLastname());
        }
        else{
            nameDto.setFirstname(null);
            nameDto.setLastname(null);
        }

        return new User.UserBuilder()
                .setUsername(userDto.getUsername())
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword())
                .setAddresses(userAddresses)
                .setFirstName(nameDto.getFirstname())
                .setLastName(nameDto.getLastname())
                .setPhone(userDto.getPhone())
                .build();
    }
    private UserDto convertUserToUserDto(User user){

        UserDto userDto = new UserDto();
        // addressDtos
        List<AddressDto> addressDtos;
        if(user.getUserAddresses() != null){
             addressDtos = new ArrayList<>();
            for(UserAddress address : user.getUserAddresses()){
                addressDtos.add(convertUserAddressToAddressDto(address));
            }
        }else{
            addressDtos = null;
        }

        userDto.setUserId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getHashedPassword());
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
