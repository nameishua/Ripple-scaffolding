package top.coderak.service;

import top.coderak.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRolesByUserId(String userId);

    List<Role> getAllRoles();

    Role getRoleById(String id);

    boolean addRole(Role role);

    boolean updateRole(Role role);

    boolean deleteRole(String id);

    boolean assignMenus(String roleId, List<String> menuIds);

    boolean assignPermissions(String roleId, List<String> permissionIds);
}
