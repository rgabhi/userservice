package learning.userservice.controllers;

import learning.userservice.dtos.UserDto;
import learning.userservice.models.User;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name="id") Long id){
        return  null;
    }
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return  null;
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name="id") Long id, @RequestBody UserDto userDto){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteUser(@PathVariable(name="id") Long id){
        return null;
    }





}
