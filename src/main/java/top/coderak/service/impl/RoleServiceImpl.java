package top.coderak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.entity.Role;
import top.coderak.mapper.RoleMapper;
import top.coderak.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUserId(String userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectAllRoles();
    }

    @Override
    public Role getRoleById(String id) {
        return roleMapper.selectById(id);
    }

    @Override
    @Transactional
    public boolean addRole(Role role) {
        role.setId(GenerateSequenceUtil.generateSequenceNo());
        role.setFlag("启用");
        return roleMapper.insert(role) > 0;
    }

    @Override
    @Transactional
    public boolean updateRole(Role role) {
        return roleMapper.updateById(role) > 0;
    }

    @Override
    @Transactional
    public boolean deleteRole(String id) {
        Role role = roleMapper.selectById(id);
        if (role != null) {
            role.setFlag("删除");
            return roleMapper.updateById(role) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean assignMenus(String roleId, List<String> menuIds) {
        roleMapper.deleteRoleMenus(roleId);
        if (menuIds != null && !menuIds.isEmpty()) {
            for (String menuId : menuIds) {
                roleMapper.insertRoleMenu(roleId, menuId);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean assignPermissions(String roleId, List<String> permissionIds) {
        roleMapper.deleteRolePermissions(roleId);
        if (permissionIds != null && !permissionIds.isEmpty()) {
            for (String permissionId : permissionIds) {
                roleMapper.insertRolePermission(roleId, permissionId);
            }
        }
        return true;
    }
}
