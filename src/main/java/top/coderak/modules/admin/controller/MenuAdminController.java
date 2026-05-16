package top.coderak.modules.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.coderak.core.api.ApiResult;
import top.coderak.entity.Menu;
import top.coderak.service.MenuService;

import java.util.List;

@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/admin/menu")
public class MenuAdminController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("获取所有菜单树")
    @GetMapping("/tree")
    public ApiResult<List<Menu>> getMenuTree() {
        return ApiResult.ok(menuService.getMenuTree());
    }

    @ApiOperation("获取所有菜单列表")
    @GetMapping("/list")
    public ApiResult<List<Menu>> list() {
        return ApiResult.ok(menuService.getAllMenus());
    }

    @ApiOperation("添加菜单")
    @PostMapping("/add")
    public ApiResult<Void> add(@RequestBody Menu menu) {
        boolean result = menuService.addMenu(menu);
        return result ? ApiResult.ok() : ApiResult.fail("Add menu failed");
    }

    @ApiOperation("更新菜单")
    @PostMapping("/update")
    public ApiResult<Void> update(@RequestBody Menu menu) {
        boolean result = menuService.updateMenu(menu);
        return result ? ApiResult.ok() : ApiResult.fail("Update menu failed");
    }

    @ApiOperation("删除菜单")
    @PostMapping("/delete")
    public ApiResult<Void> delete(@ApiParam("菜单ID") @RequestParam String id) {
        boolean result = menuService.deleteMenu(id);
        return result ? ApiResult.ok() : ApiResult.fail("Delete menu failed");
    }

    @ApiOperation("根据ID获取菜单")
    @GetMapping("/{id}")
    public ApiResult<Menu> getById(@PathVariable String id) {
        List<Menu> menus = menuService.getAllMenus();
        Menu menu = menus.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
        return ApiResult.ok(menu);
    }
}
