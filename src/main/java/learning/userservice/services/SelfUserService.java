package learning.userservice.services;

import learning.userservice.exceptions.UserAlreadyExistsException;
import learning.userservice.exceptions.UserNotFoundException;
import learning.userservice.models.User;
import learning.userservice.models.UserAddress;
import learning.userservice.repositories.UserAddressRepository;
import learning.userservice.repositories.UserRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfUserService")
public class SelfUserService implements UserService{
    UserRepository userRepository;
    UserAddressRepository userAddressRepository;
    @Autowired
    public SelfUserService(UserRepository userRepository,
                           UserAddressRepository userAddressRepository){
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public User getUser(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id: "+ id + " not found.");
        }
        return userOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        // check mandatory fields

        //check if user already exists
        Optional<User> userOptional = userRepository.
                findByEmailOrUsername(user.getEmail(), user.getUsername());
        if(userOptional.isPresent()){
            if(userOptional.get().getEmail().equals(user.getEmail())){
                throw new UserAlreadyExistsException("User with emailId: " + user.getEmail() + " already exists.");
            }
            else if(userOptional.get().getUsername().equals(user.getUsername())){
                throw new UserAlreadyExistsException("User with username: " + user.getUsername() + " already exists.");
            }
        }
        user.setUserAddresses(userAddressRepository.saveAll(user.getUserAddresses()));
        return userRepository.save(user);
        // Address
    }

    @Override
    public User updateUser(Long id, User updateUser) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("user with id: " + id + " not found.");
        }
        User user = userOptional.get();
        user.setFirstName(updateUser.getFirstName() != null ? updateUser.getFirstName() : user.getFirstName());
        user.setLastName(updateUser.getLastName() != null ? updateUser.getLastName() : user.getLastName());
        user.setPassword(updateUser.getPassword() != null ? updateUser.getPassword() : user.getPassword());
        user.setUsername(updateUser.getUsername() != null ? updateUser.getUsername() : user.getUsername());
        user.setPhone(updateUser.getPhone() != null ? updateUser.getPhone() : user.getPhone());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        userAddressRepository.deleteAllByUser_Id(id);
    }
}
