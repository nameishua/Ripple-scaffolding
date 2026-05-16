package top.coderak.service;

import top.coderak.core.base.bean.PageInfo;
import top.coderak.core.base.manager.BaseCRUDManager;
import top.coderak.entity.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService extends BaseCRUDManager<User> {

    boolean addUser(String name, String age, String id, String flag, String sort, String password, String account);

    boolean deleteUser(String id);

    User findUserByName(String userName);

    List<User> findAllXml() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException;

    List<User> findAllAnnotation();

    List<User> findAllByMyPlus(String name);

    PageInfo findPage(String name, String pageIndex, String pageSize)
            throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, SecurityException;

    boolean addUserWithRoles(User user, List<String> roleIds);

    boolean updateUserWithRoles(User user, List<String> roleIds);

    boolean deleteUserWithRoles(String id);
}
