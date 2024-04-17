package firsttry.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="Profile")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer id;
    @Column(unique = true)
    private String authority;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Permission> authorities = new ArrayList<>();

}
