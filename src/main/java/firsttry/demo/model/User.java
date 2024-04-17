package firsttry.demo.model;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", initialValue = 10000, allocationSize = 1)
    @Column(name="id")
    protected Long id;


    protected String username;
    protected String firstname;
    protected String lastname;
    private String password;
    private String email;
    private String Telephone;
    private String civilite;
    private String adresse;
    private String departement;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE")
    private Set<Role> authorities;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    public User(String username) {
        this.username = username;
    }
}
