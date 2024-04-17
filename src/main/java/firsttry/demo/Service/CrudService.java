package firsttry.demo.Service;

import firsttry.demo.DTO.UserVo;
import firsttry.demo.Repository.UserRepository;
import firsttry.demo.model.Permission;
import firsttry.demo.model.Role;
import firsttry.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CrudService implements ICrudUser {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserVo save(UserVo userVo) {
        User user = convertToUserEntity(userVo);
        if (user != null) {
            userRepository.save(user);
            return userVo;
        } else {
            return null; // Handle conversion failure
        }
    }
    private User convertToUserEntity(UserVo userVo) {
        User user = new User();
        user.setId(userVo.getId());
        user.setUsername(userVo.getUsername());

        user.setPassword(userVo.getPassword());
        user.setEmail(userVo.getEmail());


        user.setEnabled(userVo.isEnabled());
        user.setAccountNonExpired(userVo.isAccountNonExpired());
        user.setCredentialsNonExpired(userVo.isCredentialsNonExpired());
        user.setAccountNonLocked(userVo.isAccountNonLocked());
        Set<Role> roles = userVo.getAuthorities().stream()
                .map(roleVo -> {
                    Role role = new Role();
                    role.setId(roleVo.getId()); // Assuming there's an id field in RoleVo
                    role.setAuthority(roleVo.getAuthority()); // Assuming there's an authority field in RoleVo

                    // Convert PermissionVo objects to Permission entities and set them in the Role entity
                    Set<Permission> permissions = roleVo.getAuthorities().stream()
                            .map(permissionVo -> {
                                Permission permission = new Permission();
                                permission.setId(permissionVo.getId()); // Assuming there's an id field in PermissionVo
                                permission.setAuthority(permissionVo.getAuthority()); // Assuming there's an authority field in PermissionVo
                                return permission;
                            })
                            .collect(Collectors.toSet()); // Collecting permissions into a Set

                    role.setAuthorities((List<Permission>) permissions);
                    return role;
                })
                .collect(Collectors.toSet()); // Collecting roles into a Set

        user.setAuthorities(roles);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
