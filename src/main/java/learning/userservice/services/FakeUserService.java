package learning.userservice.services;

import learning.userservice.exceptions.TokenNotFoundOrExpiredException;
import learning.userservice.exceptions.UserAlreadyExistsException;
import learning.userservice.models.Token;
import learning.userservice.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("fakeUserService")
public class FakeUserService implements UserService{
    // just adding for the sake of completion
    @Override
    public User signup(String firstName, String lastName, String email, String password) throws UserAlreadyExistsException {
        return null;
    }

    @Override
    public Token login(String email, String password) {
        return null;
    }

    @Override
    public void logout(String token) {
        return;
    }

    @Override
    public User validateToken(String token) throws TokenNotFoundOrExpiredException {
        return null;
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
