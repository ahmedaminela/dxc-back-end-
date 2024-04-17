package firsttry.demo.Controller;

import firsttry.demo.DTO.RoleVo;
import firsttry.demo.DTO.TokenVo;
import firsttry.demo.DTO.UserVo;
import firsttry.demo.Request.CreateUserRequest;
import firsttry.demo.Request.UserRequest;
import firsttry.demo.Service.IUserService;
import firsttry.demo.exception.BusinessException;
import firsttry.demo.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/auth")

@AllArgsConstructor
public class AuthentificationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private IUserService userService;

    @PostMapping("/signin")
    public ResponseEntity<TokenVo> authenticateUser(@RequestBody UserRequest userRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userRequest.username(), userRequest.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            TokenVo tokenVo = TokenVo.builder().
                    jwtToken(jwt).
                    username(userRequest.username()).
                    roles(authentication.getAuthorities().stream().
                            map(GrantedAuthority::getAuthority).
                            collect(Collectors.toList())).build();
            return ResponseEntity.ok(tokenVo);
        } catch (Exception e) {
            throw new BusinessException("Login ou mot de passe incorrect");
        }
    }


    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('Creer_user')")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest createUserRequest) {
        userService.save(UserVo.builder().
                username(createUserRequest.username()).
                password(createUserRequest.password()).
                email(createUserRequest.email()).
                enabled(true).
                accountNonExpired(true).
                accountNonLocked(true).
                credentialsNonExpired(true)
                .authorities(List.of(RoleVo.builder().authority(createUserRequest.authority()).build())).
                build());

        return new ResponseEntity<>(String.format("User [%s] created with success",
                createUserRequest.username()), HttpStatus.CREATED);
    }
}
