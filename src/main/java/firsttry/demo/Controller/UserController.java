package firsttry.demo.Controller;



import firsttry.demo.DTO.UserVo;
import firsttry.demo.Service.ICrudUser;

import firsttry.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('admin')")
public class UserController {

    @Autowired
    private ICrudUser userService;

    // Get all users
    @GetMapping
    @PreAuthorize("hasAuthority('Get_all_users')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by ID

    // Create a new user
    @PostMapping
    @PreAuthorize("hasAuthority('Creer_user')")
    public ResponseEntity<UserVo> createUser(@RequestBody UserVo user) {
        UserVo createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Update user


    // Delete user by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Supprimer_user')")

    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Remove associations in profile_authorities table
            user.setAuthorities(new HashSet<>()); // Assuming authorities is the association

            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Get_User_byid')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }

}
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Modifier_user')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserVo userVo) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            // Update user fields
            existingUser.setUsername(userVo.getUsername());
            existingUser.setEmail(userVo.getEmail());
            // Update other fields as needed

            User updatedUser = userService.updateUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }}

