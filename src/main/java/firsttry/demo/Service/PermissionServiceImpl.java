package firsttry.demo.Service;

import firsttry.demo.Repository.PermissionRepository;
import firsttry.demo.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> getPermissionById(Integer id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Permission createPermission(Permission permission) {
        // Add logic to handle duplicate permission names if necessary
        return permissionRepository.save(permission);
    }

    @Override
    public Permission updatePermission(Long id, Permission newPermission) {
        // Add logic to handle updating existing permissions
        return permissionRepository.save(newPermission);
    }

    @Override
    public void deletePermission(Integer id) {
        permissionRepository.deleteById(id);
    }

    // Add other methods as needed for your application
}
