package top.coderak.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.coderak.core.base.annotation.AvoidRepeatableCommit;
import top.coderak.core.base.annotation.PrintOperateType;
import top.coderak.core.base.controller.BaseController;
import top.coderak.core.base.enums.PrintOperateTypeEnum;
import top.coderak.core.base.manager.BaseCRUDManager;
import top.coderak.core.utils.ObjectUtil;
import top.coderak.entity.User;
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
    @AvoidRepeatableCommit
    @PrintOperateType(type = PrintOperateTypeEnum.ADD)
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser() {

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
    @PrintOperateType(type = PrintOperateTypeEnum.DELETE)
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public void delete() {

        String id = getParam("id");

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
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findUserByName", method = RequestMethod.POST)
    public User findByUserName(@RequestParam(value = "userName", required = true) String userName) {

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
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findAllAnnotation", method = RequestMethod.POST)
    public void findAllAnnotation() {

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
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findAllXml", method = RequestMethod.POST)
    public void findAllXml() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {

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
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findAllBase", method = RequestMethod.POST)
    public void findAllBase() {

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
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findAllByMyPlus", method = RequestMethod.POST)
    public void findAllByMyPlus() {

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
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
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
