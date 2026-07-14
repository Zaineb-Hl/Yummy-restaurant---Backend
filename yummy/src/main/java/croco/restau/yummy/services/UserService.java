package croco.restau.yummy.services;

import java.util.List;
import java.util.Optional;

import croco.restau.yummy.models.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
