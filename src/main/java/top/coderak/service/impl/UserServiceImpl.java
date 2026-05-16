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
import top.coderak.mapper.RoleMapper;
import top.coderak.mapper.UserMapper;
import top.coderak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Service
public class UserServiceImpl<T> extends BaseCRUDManagerImpl<User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SequenceServiceImpl sequenceServiceImpl;

    @Autowired
    RoleMapper roleMapper;

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

    @Override
    public List<User> findAllAnnotation() {
        return userMapper.findAllAnnotation();
    }

    @Override
    public List<User> findAllXml() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        return userMapper.findAllXml();
    }

    @Override
    public List<User> findAllByMyPlus(String name) {
        List<User> users = userMapper.findAllAnnotation();
        if (ObjectUtil.isNotNullOrEmpty(name)) {
            return users.stream().filter(u -> u.getName() != null && u.getName().contains(name)).collect(Collectors.toList());
        }
        return users;
    }

    @Override
    public PageInfo findPage(String name, String pageIndex, String pageSize)
            throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, SecurityException {
        List<User> users = findAllByMyPlus(name);
        PageInfo pageInfo = PageHandlerUtil.pageHandler(pageIndex, pageSize);
        pageInfo.setPageValue(users);
        return pageInfo;
    }

    @Override
    @Transactional
    public boolean addUserWithRoles(User user, List<String> roleIds) {
        user.setId(GenerateSequenceUtil.generateSequenceNo());
        user.setPassword(MD5Utils.string2MD5(user.getPassword()));
        user.setFlag("启用");
        boolean result = userMapper.insert(user) > 0;
        if (result && roleIds != null && !roleIds.isEmpty()) {
            for (String roleId : roleIds) {
                roleMapper.insertUserRole(user.getId(), roleId);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public boolean updateUserWithRoles(User user, List<String> roleIds) {
        boolean result = userMapper.updateById(user) > 0;
        if (result) {
            roleMapper.deleteUserRoles(user.getId());
            if (roleIds != null && !roleIds.isEmpty()) {
                for (String roleId : roleIds) {
                    roleMapper.insertUserRole(user.getId(), roleId);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional
    public boolean deleteUserWithRoles(String id) {
        roleMapper.deleteUserRoles(id);
        User user = new User();
        user.setId(id);
        user.setFlag("删除");
        return userMapper.updateById(user) > 0;
    }
}
