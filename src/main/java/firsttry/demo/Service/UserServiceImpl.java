package firsttry.demo.Service;

import firsttry.demo.DTO.PermissionVo;
import firsttry.demo.DTO.RoleVo;
import firsttry.demo.DTO.UserVo;
import firsttry.demo.Repository.PermissionRepository;
import firsttry.demo.Repository.RoleRepository;
import firsttry.demo.Repository.UserRepository;
import firsttry.demo.model.Permission;
import firsttry.demo.model.Role;
import firsttry.demo.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo userVo = modelMapper.map(userRepository.findByUsername(username), UserVo.class);
        List<RoleVo> permissions = new ArrayList<>();
        userVo.getAuthorities().forEach(
                roleVo -> roleVo.getAuthorities().forEach(
                        permission -> permissions.add(
                                RoleVo.builder().authority(permission.getAuthority()).build())));
        userVo.getAuthorities().addAll(permissions);
        return userVo;
    }

    @Override

    public void save(UserVo userVo) {
        User user = modelMapper.map(userVo, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = userVo.getAuthorities().stream()
                .map(roleVo -> roleRepository.findByAuthority(roleVo.getAuthority())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found for authority: " + roleVo.getAuthority())))
                .collect(Collectors.toSet());
        user.setAuthorities(roles);

        userRepository.save(user);
    }

    @Override

    public void save(RoleVo roleVo) {
        Role role = modelMapper.map(roleVo, Role.class);

        List<Permission> permissions = roleVo.getAuthorities().stream()
                .map(permissionVo -> permissionRepository.findByAuthority(permissionVo.getAuthority())
                        .orElseThrow(() -> new IllegalArgumentException("Permission not found for authority: " + permissionVo.getAuthority())))
                .collect(Collectors.toList());
        role.setAuthorities(permissions); // Setting List<Permission> directly

        roleRepository.save(role);
    }


    @Override
    public void save(PermissionVo permissionVo) {
        Permission permission = modelMapper.map(permissionVo, Permission.class);
        permissionRepository.save(permission);
    }

    @Override
    public RoleVo getRoleByName(String authority) {
        Role role = roleRepository.findByAuthority(authority)
                .orElseThrow(() -> new IllegalArgumentException("Role not found for authority: " + authority));
        return modelMapper.map(role, RoleVo.class);
    }

    @Override
    public PermissionVo getPermissionByName(String authority) {
        Permission permission = permissionRepository.findByAuthority(authority)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found for authority: " + authority));
        return modelMapper.map(permission, PermissionVo.class);
    }
}
