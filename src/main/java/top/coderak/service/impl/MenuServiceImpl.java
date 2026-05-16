package top.coderak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.entity.Menu;
import top.coderak.entity.Role;
import top.coderak.mapper.MenuMapper;
import top.coderak.mapper.RoleMapper;
import top.coderak.service.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Menu> getMenusByUserId(String userId) {
        List<Role> roles = roleMapper.selectRolesByUserId(userId);
        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        return menuMapper.selectMenusByRoleIds(roleIds);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.selectAllMenus();
    }

    @Override
    public List<Menu> getMenuTree() {
        List<Menu> allMenus = menuMapper.selectMenuTree();
        return buildMenuTree(allMenus, null);
    }

    private List<Menu> buildMenuTree(List<Menu> menus, String parentId) {
        return menus.stream()
                .filter(menu -> (parentId == null && menu.getParentId() == null) ||
                        (parentId != null && parentId.equals(menu.getParentId())))
                .peek(menu -> menu.setChildren(buildMenuTree(menus, menu.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean addMenu(Menu menu) {
        menu.setId(GenerateSequenceUtil.generateSequenceNo());
        menu.setFlag("启用");
        return menuMapper.insert(menu) > 0;
    }

    @Override
    @Transactional
    public boolean updateMenu(Menu menu) {
        return menuMapper.updateById(menu) > 0;
    }

    @Override
    @Transactional
    public boolean deleteMenu(String id) {
        Menu menu = menuMapper.selectById(id);
        if (menu != null) {
            menu.setFlag("删除");
            return menuMapper.updateById(menu) > 0;
        }
        return false;
    }
}
