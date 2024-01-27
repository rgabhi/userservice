package learning.userservice.services;

import learning.userservice.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("fakeUserService")
public class FakeUserService implements UserService{
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
