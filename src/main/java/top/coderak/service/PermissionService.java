package top.coderak.service;

import top.coderak.entity.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> getPermissionsByUserId(String userId);

    List<Permission> getAllPermissions();

    boolean addPermission(Permission permission);

    boolean updatePermission(Permission permission);

    boolean deletePermission(String id);
}
