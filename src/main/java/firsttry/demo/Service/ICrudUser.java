package firsttry.demo.Service;

import firsttry.demo.DTO.UserVo;
import firsttry.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface ICrudUser {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    UserVo save(UserVo user);
    void deleteUser(Long id);
    public User updateUser(User user);

}
