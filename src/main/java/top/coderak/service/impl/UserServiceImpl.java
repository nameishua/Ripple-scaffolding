package top.coderak.service.impl;

import org.springframework.transaction.annotation.Transactional;
import top.coderak.core.base.bean.PageInfo;
import top.coderak.core.base.constants.UserConstants;
import top.coderak.core.base.manager.impl.BaseCRUDManagerImpl;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.core.utils.MD5Utils;
import top.coderak.core.utils.ObjectUtil;
import top.coderak.core.utils.PageHandlerUtil;
import top.coderak.entity.User;
import top.coderak.mapper.UserMapper;
import top.coderak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.coderak.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户操作实现类
 *
 * @param <T>
 * @author zyh
 * @date 2019/7/21 0021
 */
@SuppressWarnings("ALL")
@Service
public class UserServiceImpl<T> extends BaseCRUDManagerImpl<User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SequenceServiceImpl sequenceServiceImpl;

    @Override
    @Transactional
    public boolean addUser(String name, String age, String id, String flag, String sort, String password,
                           String account) {

        boolean theFlag = false;

        User user = new User();

        user.setName(name);

        if (ObjectUtil.isNotNullOrEmpty(age)) {

            user.setAge(Integer.valueOf(age));
        }

        user.setFlag(flag);

        user.setAccount(account);

        if (ObjectUtil.isNotNullOrEmpty(sort)) {

            user.setSort(Integer.valueOf(sort));
        }

        if (ObjectUtil.isNotNullOrEmpty(id)) {

            user.setId(id);

            user.setPassword(MD5Utils.string2MD5(password));

            try {

                userMapper.updateById(user);

                theFlag = true;
            } catch (Exception e) {

                System.out.println("修改失败!");

                e.printStackTrace();
            }

            return theFlag;
        } else {

            user.setId(GenerateSequenceUtil.generateSequenceNo());

            user.setPassword(MD5Utils.string2MD5(password));

            String code = sequenceServiceImpl.getSequence(UserConstants.USER_CODE);

            user.setCode(code);

            try {

                userMapper.insert(user);

                theFlag = true;
            } catch (Exception e) {

                System.out.println("新增失败!");

                e.printStackTrace();
            }
            return theFlag;
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(String id) {

        boolean flag = false;

        try {

            userMapper.deleteUser(id);

            flag = true;
        } catch (Exception e) {

            System.out.println("删除失败!");

            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public User findUserByName(String userName) {
        return userMapper.findByName(userName);
    }

    /*
     * 注解方式 废弃
     */
    @Override
    public List<User> findAllAnnotation() {
        //用mybatis plus的方法
        //return userMapper.selectList(null);

        //可代替此注解方式
        return userMapper.findAllAnnotation();
    }

    @Override
    public List<User> findAllXml() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        /**
         * 反射测试
         */
        Class<User> user = User.class;// 得到类类型

        Object newInstance = user.newInstance();// 得到Students的无参构造函数

        // 根据方法名和参数类型得到指定方法 方法名 参数类型 参数类型
        Method show = user.getDeclaredMethod("show");// 得到共有方法

        // 调用方法 指定对象 参数 参数
        Object invoke = show.invoke(newInstance);

        System.out.println(invoke);

        return userMapper.findAllXml();
    }

    @Override
    public List<User> findAllByMyPlus(String name) {

        Map<String, Object> columnMap = new HashMap<String, Object>();

        columnMap.put("NAME", name);

        return userMapper.selectByMap(columnMap);
    }

    @Override
    public PageInfo findPage(String name, String pageIndex, String pageSize)
            throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, SecurityException {

        Map<String, Object> columnMap = new HashMap<String, Object>();

        columnMap.put("NAME", name);

        // 执行查询
        PageInfo pageInfo = PageHandlerUtil.pageHandler(pageIndex, pageSize);

        pageInfo.setPageValue(userMapper.selectByMap(columnMap));

        return pageInfo;

    }
}
