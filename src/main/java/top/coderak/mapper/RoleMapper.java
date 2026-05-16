package top.coderak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.coderak.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> selectRolesByUserId(@Param("userId") String userId);

    List<Role> selectAllRoles();

    int insert(Role role);

    int updateById(Role role);

    Role selectById(@Param("id") String id);

    void insertUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    void deleteUserRoles(@Param("userId") String userId);

    void insertRoleMenu(@Param("roleId") String roleId, @Param("menuId") String menuId);

    void deleteRoleMenus(@Param("roleId") String roleId);

    void insertRolePermission(@Param("roleId") String roleId, @Param("permissionId") String permissionId);

    void deleteRolePermissions(@Param("roleId") String roleId);
}
