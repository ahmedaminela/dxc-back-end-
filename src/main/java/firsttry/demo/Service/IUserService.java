package firsttry.demo.Service;

import firsttry.demo.DTO.PermissionVo;
import firsttry.demo.DTO.RoleVo;
import firsttry.demo.DTO.UserVo;

public interface IUserService {
    void save(UserVo user);
    void save(RoleVo role);
    void save(PermissionVo vo);
    RoleVo getRoleByName(String role);
    PermissionVo getPermissionByName(String authority);
}
