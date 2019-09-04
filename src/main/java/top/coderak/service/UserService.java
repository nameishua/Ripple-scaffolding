package top.coderak.service;

import top.coderak.core.base.bean.PageInfo;
import top.coderak.core.base.manager.BaseCRUDManager;
import top.coderak.entity.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 用户接口
 *
 * @author zyh
 * @date 2019/7/21 0021
 */
public interface UserService extends BaseCRUDManager<User> {

    /**
     * 保存用户
     *
     * @return boolean
     * @author zyh
     * @date 2019/7/21 0021
     */
    boolean addUser(String name, String age, String id, String flag, String sort, String password, String account);

    /**
     * 删除用户
     *
     * @param id
     * @return boolean
     * @author zyh
     * @date 2019/7/21 0021
     */
    boolean deleteUser(String id);

    /**
     * 根据用户名字查询用户信息
     *
     * @param userName
     * @return top.coderak.entity.User
     * @author zyh
     * @date 2019/7/21 0021
     */
    User findUserByName(String userName);

    /**
     * 查询所有
     *
     * @param
     * @return java.util.List<top.coderak.entity.User>
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @author zyh
     * @date 2019/7/21 0021
     */
    List<User> findAllXml() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException;

    /**
     * 查询所有
     *
     * @param
     * @return java.util.List<top.coderak.entity.User>
     * @author zyh
     * @date 2019/7/21 0021
     */
    List<User> findAllAnnotation();

    List<User> findAllByMyPlus(String name);

    PageInfo findPage(String name, String pageIndex, String pageSize)
            throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, SecurityException;
}
