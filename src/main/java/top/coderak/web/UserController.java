package top.coderak.web;

import top.coderak.core.base.controller.BaseController;
import top.coderak.core.base.manager.BaseCRUDManager;
import top.coderak.core.utils.ObjectUtil;
import top.coderak.entity.User;
import top.coderak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.coderak.core.base.controller.BaseController;
import top.coderak.core.base.manager.BaseCRUDManager;
import top.coderak.core.utils.ObjectUtil;
import top.coderak.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * UserController
 *
 * @author zyh
 * @date 2019/7/21 0021
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private BaseCRUDManager<User> baseMysqlCRUDManager;

    /**
     * 保存用户
     *
     * @return boolean
     * @author zyh
     * @date 2019/7/21 0021
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser() {

        System.out.println("新增...");

        String id = getParam("id");

        String name = getParam("name");

        String age = getParam("age");

        String flag = getParam("flag");

        String sort = getParam("sort");

        String password = getParam("password");

        String account = getParam("account");

        userService.addUser(name, age, id, flag, sort, password, account);

        writeSuccessResult();
    }

    /**
     * 删除用户
     *
     * @param
     * @return boolean
     * @author zyh
     * @date 2019/7/21 0021
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public void delete() {

        String id = getParam("id");

        System.out.println("删除...");

        userService.deleteUser(id);

        writeSuccessResult();
    }

    /**
     * 功能描述
     *
     * @param userName
     * @return top.coderak.entity.User
     * @author zyh
     * @date 2019/7/21 0021
     */
    @RequestMapping(value = "/findUserByName", method = RequestMethod.POST)
    public User findByUserName(@RequestParam(value = "userName", required = true) String userName) {

        System.out.println("查询...");

        return userService.findUserByName(userName);
    }

    /**
     * 注解方式
     *
     * @param
     * @return java.util.List<top.coderak.entity.User>
     * @author zyh
     * @date 2019/7/21 0021
     */
    @RequestMapping(value = "/findAllAnnotation", method = RequestMethod.POST)
    public void findAllAnnotation() {

        System.out.println("查询所有数据...");

        writeSuccessResult(userService.findAllAnnotation());
    }

    /**
     * XML方式
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
    @RequestMapping(value = "/findAllXml", method = RequestMethod.POST)
    public void findAllXml() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        System.out.println("查询所有数据...");

        writeSuccessResult(userService.findAllXml());
    }

    /**
     * XML方式
     *
     * @param
     * @return java.util.List<top.coderak.entity.User>
     * @author zyh
     * @date 2019/7/21 0021
     */
    @RequestMapping(value = "/findAllBase", method = RequestMethod.POST)
    public void findAllBase() {

        System.out.println("查询所有数据...");

        User user = new User();

        user.setName(getParam("name"));

        if (ObjectUtil.isNotNullOrEmpty(getParam("age"))) {

            user.setAge(Integer.valueOf(getParam("age")));
        }

        user.setId(getParam("id"));

        System.out.println(user);

        writeSuccessResult((List<User>) baseMysqlCRUDManager.query(user));
    }

    /**
     * Mybatis-Plus测试
     *
     * @param
     * @return java.util.List<top.coderak.entity.User>
     * @author zyh
     * @date 2019/7/25 0021
     */
    @RequestMapping(value = "/findAllByMyPlus", method = RequestMethod.POST)
    public void findAllByMyPlus() {

        System.out.println("开始查询所有数据...");

        String name = getParam("name");

        writeSuccessResult(userService.findAllByMyPlus(name));
    }

    /**
     * findPage
     *
     * @param
     * @return java.util.List<top.coderak.entity.User>
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NumberFormatException
     * @author zyh
     * @date 2019/7/25 0021
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public void findPage() throws NumberFormatException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {

        String name = getParam("name");

        String age = getParam("age");

        String pageIndex = getParam("pageIndex");

        String pageSize = getParam("pageSize");

        User user = new User();

        user.setName(name);

        user.setAge(Integer.valueOf(age));

        System.out.println(userService.query(user));

        writeSuccessResult(userService.findPage(name, pageIndex, pageSize));
    }
}
