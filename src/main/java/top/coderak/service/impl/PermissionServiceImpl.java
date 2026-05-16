package top.coderak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.entity.Permission;
import top.coderak.entity.Role;
import top.coderak.mapper.PermissionMapper;
import top.coderak.mapper.RoleMapper;
import top.coderak.service.PermissionService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Permission> getPermissionsByUserId(String userId) {
        List<Role> roles = roleMapper.selectRolesByUserId(userId);
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        return permissionMapper.selectPermissionsByRoleIds(roleIds);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionMapper.selectAllPermissions();
    }

    @Override
    @Transactional
    public boolean addPermission(Permission permission) {
        permission.setId(GenerateSequenceUtil.generateSequenceNo());
        permission.setFlag("启用");
        return permissionMapper.insert(permission) > 0;
    }

    @Override
    @Transactional
    public boolean updatePermission(Permission permission) {
        return permissionMapper.updateById(permission) > 0;
    }

    @Override
    @Transactional
    public boolean deletePermission(String id) {
        Permission permission = permissionMapper.selectById(id);
        if (permission != null) {
            permission.setFlag("删除");
            return permissionMapper.updateById(permission) > 0;
        }
        return false;
    }
}
