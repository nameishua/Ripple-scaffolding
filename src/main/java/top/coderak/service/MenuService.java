package top.coderak.service;

import top.coderak.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMenusByUserId(String userId);

    List<Menu> getAllMenus();

    List<Menu> getMenuTree();

    boolean addMenu(Menu menu);

    boolean updateMenu(Menu menu);

    boolean deleteMenu(String id);
}
