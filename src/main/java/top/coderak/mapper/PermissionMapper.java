package top.coderak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.coderak.entity.Permission;

import java.util.List;

@Mapper
public interface PermissionMapper {

    List<Permission> selectPermissionsByRoleIds(@Param("roleIds") List<String> roleIds);

    List<Permission> selectAllPermissions();

    int insert(Permission permission);

    int updateById(Permission permission);

    Permission selectById(@Param("id") String id);
}
