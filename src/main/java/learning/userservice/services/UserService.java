package learning.userservice.services;

import learning.userservice.models.User;

import java.util.List;

public interface UserService {
    public User getUser(Long id);
    public List<User> getAllUsers();
    public User addUser(User user);
    public User updateUser(Long id, User user);
    public void deleteUser(Long id);

}
