package firsttry.demo.Repository;

import firsttry.demo.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    // You can define additional query methods here if needed
    Optional<Permission> findByAuthority(String authority);

}