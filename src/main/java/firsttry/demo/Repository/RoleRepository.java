package firsttry.demo.Repository;

import firsttry.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    // You can define additional query methods here if needed
    Optional<Role> findByAuthority(String authority);
    List<Role> findAll();
}
