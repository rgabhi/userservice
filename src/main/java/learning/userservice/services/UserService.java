package learning.userservice.services;

import learning.userservice.exceptions.*;
import learning.userservice.models.Token;
import learning.userservice.models.User;

import java.util.List;

public interface UserService {
    public User signup(String firstName, String lastName, String email, String password) throws UserAlreadyExistsException;

    public Token login(String email, String password) throws UserNotFoundException, InvalidFieldException;

    public void logout(String token) throws TokenNotFoundOrExpiredException;

    public User validateToken(String token) throws TokenNotFoundOrExpiredException;
    public User getUser(Long id) throws UserNotFoundException;
    public List<User> getAllUsers();
    public User addUser(User user) throws UserAlreadyExistsException, EmptyRequiredFieldException;
    public User updateUser(Long id, User user) throws UserNotFoundException;
    public void deleteUser(Long id);

}
