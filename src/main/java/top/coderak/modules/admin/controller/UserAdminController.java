package top.coderak.modules.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.coderak.core.api.ApiResult;
import top.coderak.core.utils.JWTUtils;
import top.coderak.entity.Menu;
import top.coderak.entity.Permission;
import top.coderak.entity.Role;
import top.coderak.entity.User;
import top.coderak.mapper.RoleMapper;
import top.coderak.mapper.UserMapper;
import top.coderak.service.MenuService;
import top.coderak.service.PermissionService;
import top.coderak.service.RoleService;
import top.coderak.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleMapper roleMapper;

    private String getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return JWTUtils.getAccount(token);
        }
        return null;
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/info")
    public ApiResult<Map<String, Object>> getUserInfo(HttpServletRequest request) {
        String account = getUserIdFromToken(request);
        if (account == null) {
            return ApiResult.fail("User not logged in");
        }
        User user = userMapper.selectByAccount(account);
        List<Role> roles = roleService.getRolesByUserId(user.getId());
        List<Menu> menus = menuService.getMenusByUserId(user.getId());
        List<Permission> permissions = permissionService.getPermissionsByUserId(user.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("roles", roles);
        data.put("menus", menus);
        data.put("permissions", permissions);
        return ApiResult.ok(data);
    }

    @ApiOperation("获取所有用户列表")
    @GetMapping("/list")
    public ApiResult<List<User>> list() {
        List<User> users = userService.findAllAnnotation();
        users.forEach(user -> user.setPassword(null));
        return ApiResult.ok(users);
    }

    @ApiOperation("获取所有角色列表")
    @GetMapping("/roles")
    public ApiResult<List<Role>> getAllRoles() {
        return ApiResult.ok(roleService.getAllRoles());
    }

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public ApiResult<Void> add(@RequestBody User user, @RequestParam(required = false) List<String> roleIds) {
        boolean result = userService.addUserWithRoles(user, roleIds);
        return result ? ApiResult.ok() : ApiResult.fail("Add user failed");
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    public ApiResult<Void> update(@RequestBody User user, @RequestParam(required = false) List<String> roleIds) {
        boolean result = userService.updateUserWithRoles(user, roleIds);
        return result ? ApiResult.ok() : ApiResult.fail("Update user failed");
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public ApiResult<Void> delete(@ApiParam("用户ID") @RequestParam String id) {
        boolean result = userService.deleteUserWithRoles(id);
        return result ? ApiResult.ok() : ApiResult.fail("Delete user failed");
    }

    @ApiOperation("根据ID获取用户")
    @GetMapping("/{id}")
    public ApiResult<User> getById(@PathVariable String id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setPassword(null);
            List<Role> roles = roleService.getRolesByUserId(id);
            user.setRoles(roles);
        }
        return ApiResult.ok(user);
    }
}
