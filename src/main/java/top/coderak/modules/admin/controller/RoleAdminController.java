package top.coderak.modules.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.coderak.core.api.ApiResult;
import top.coderak.entity.Menu;
import top.coderak.entity.Permission;
import top.coderak.entity.Role;
import top.coderak.service.MenuService;
import top.coderak.service.PermissionService;
import top.coderak.service.RoleService;

import java.util.List;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("获取所有角色列表")
    @GetMapping("/list")
    public ApiResult<List<Role>> list() {
        return ApiResult.ok(roleService.getAllRoles());
    }

    @ApiOperation("获取所有菜单列表")
    @GetMapping("/menus")
    public ApiResult<List<Menu>> getAllMenus() {
        return ApiResult.ok(menuService.getAllMenus());
    }

    @ApiOperation("获取所有权限列表")
    @GetMapping("/permissions")
    public ApiResult<List<Permission>> getAllPermissions() {
        return ApiResult.ok(permissionService.getAllPermissions());
    }

    @ApiOperation("添加角色")
    @PostMapping("/add")
    public ApiResult<Void> add(@RequestBody Role role) {
        boolean result = roleService.addRole(role);
        return result ? ApiResult.ok() : ApiResult.fail("Add role failed");
    }

    @ApiOperation("更新角色")
    @PostMapping("/update")
    public ApiResult<Void> update(@RequestBody Role role) {
        boolean result = roleService.updateRole(role);
        return result ? ApiResult.ok() : ApiResult.fail("Update role failed");
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public ApiResult<Void> delete(@ApiParam("角色ID") @RequestParam String id) {
        boolean result = roleService.deleteRole(id);
        return result ? ApiResult.ok() : ApiResult.fail("Delete role failed");
    }

    @ApiOperation("分配菜单给角色")
    @PostMapping("/assignMenus")
    public ApiResult<Void> assignMenus(
            @ApiParam("角色ID") @RequestParam String roleId,
            @RequestBody List<String> menuIds) {
        boolean result = roleService.assignMenus(roleId, menuIds);
        return result ? ApiResult.ok() : ApiResult.fail("Assign menus failed");
    }

    @ApiOperation("分配权限给角色")
    @PostMapping("/assignPermissions")
    public ApiResult<Void> assignPermissions(
            @ApiParam("角色ID") @RequestParam String roleId,
            @RequestBody List<String> permissionIds) {
        boolean result = roleService.assignPermissions(roleId, permissionIds);
        return result ? ApiResult.ok() : ApiResult.fail("Assign permissions failed");
    }

    @ApiOperation("根据ID获取角色")
    @GetMapping("/{id}")
    public ApiResult<Role> getById(@PathVariable String id) {
        return ApiResult.ok(roleService.getRoleById(id));
    }
}
