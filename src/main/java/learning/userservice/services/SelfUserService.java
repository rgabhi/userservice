package learning.userservice.services;

import learning.userservice.models.User;
import learning.userservice.repositories.GeolocationRepository;
import learning.userservice.repositories.UserAddressRepository;
import learning.userservice.repositories.UserNameRepository;
import learning.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("selfUserService")
public class SelfUserService implements UserService{
    UserRepository userRepository;
    UserNameRepository userNameRepository;
    UserAddressRepository userAddressRepository;
    GeolocationRepository geolocationRepository;
    @Autowired
    public SelfUserService(UserRepository userRepository,
                           UserNameRepository userNameRepository,
                           UserAddressRepository userAddressRepository,
                           GeolocationRepository geolocationRepository){
        this.userRepository = userRepository;
        this.userNameRepository = userNameRepository;
        this.userAddressRepository = userAddressRepository;
        this.geolocationRepository = geolocationRepository;
    }

    @Override
    public User getUser(Long id) {

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User updateUser(Long id, User user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
