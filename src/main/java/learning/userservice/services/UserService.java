package learning.userservice.services;

import learning.userservice.exceptions.UserAlreadyExistsException;
import learning.userservice.exceptions.UserNotFoundException;
import learning.userservice.models.User;

import java.util.List;

public interface UserService {
    public User getUser(Long id) throws UserNotFoundException;
    public List<User> getAllUsers();
    public User addUser(User user) throws UserAlreadyExistsException;
    public User updateUser(Long id, User user) throws UserNotFoundException;
    public void deleteUser(Long id);

}
