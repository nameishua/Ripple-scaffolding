package top.coderak.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = "用户管理接口")
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private BaseCRUDManager<User> baseMysqlCRUDManager;

    @ApiOperation("添加用户")
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

    @ApiOperation("删除用户")
    @PrintOperateType(type = PrintOperateTypeEnum.DELETE)
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public void delete() {
        String id = getParam("id");
        userService.deleteUser(id);
        writeSuccessResult();
    }

    @ApiOperation("根据用户名查询用户")
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findUserByName", method = RequestMethod.POST)
    public User findByUserName(@ApiParam(value = "用户名", required = true) @RequestParam(value = "userName", required = true) String userName) {
        return userService.findUserByName(userName);
    }

    @ApiOperation("查询所有用户(注解方式)")
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findAllAnnotation", method = RequestMethod.POST)
    public void findAllAnnotation() {
        writeSuccessResult(userService.findAllAnnotation());
    }

    @ApiOperation("查询所有用户(XML方式)")
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findAllXml", method = RequestMethod.POST)
    public void findAllXml() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        writeSuccessResult(userService.findAllXml());
    }

    @ApiOperation("基础查询")
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

    @ApiOperation("Mybatis-Plus查询")
    @PrintOperateType(type = PrintOperateTypeEnum.SELECT)
    @RequestMapping(value = "/findAllByMyPlus", method = RequestMethod.POST)
    public void findAllByMyPlus() {
        String name = getParam("name");
        writeSuccessResult(userService.findAllByMyPlus(name));
    }

    @ApiOperation("分页查询")
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