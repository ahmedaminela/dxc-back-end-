package firsttry.demo.Service;

import firsttry.demo.model.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    List<Permission> getAllPermissions();

    Optional<Permission> getPermissionById(Integer id);

    Permission createPermission(Permission permission);

    Permission updatePermission(Long id, Permission newPermission);

    void deletePermission(Integer id);

    // Add other methods as needed for your application
}
