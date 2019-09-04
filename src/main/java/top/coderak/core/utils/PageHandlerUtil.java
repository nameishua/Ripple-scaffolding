package top.coderak.core.utils;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import top.coderak.core.base.bean.PageInfo;
import top.coderak.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import top.coderak.core.base.bean.PageInfo;
import top.coderak.mapper.UserMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageHandlerUtil {

    @Autowired
    static SqlSession sqlSession;

    public static <T> PageInfo pageHandler(T pageIndex, T pageSize) {

        // 设置分页参数
        PageHelper.startPage(Integer.valueOf(pageIndex.toString()), Integer.valueOf(pageSize.toString()));

        // 返回的分页参数
        Pagination pagination = PageHelper.getPagination();

        PageInfo pageInfo = new PageInfo();

        pageInfo.setPageInfo(pagination);

        return pageInfo;
    }

    public static <T> Map<String, Object> pageHandler(Class<? extends BaseMapper<T>> class1, EntityWrapper<T> wrapper,
                                                      String string, Integer pageIndex, Integer pageSize) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {

        // 设置分页参数
        PageHelper.startPage(pageIndex, pageSize);

        // 返回的分页参数
        Pagination pagination = PageHelper.getPagination();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("pageInfo", pagination);

        @SuppressWarnings("unused")
        List<Object> list = new ArrayList<Object>();


        /**********/
        try {

//			Class interfaceImpl = Class.forName(T);// 这里要写全类名
            @SuppressWarnings("rawtypes")
            Class interfaceImpl = UserMapper.class;
//			Object instance = Proxy.newProxyInstance(interfaceImpl.getClassLoader(), new Class[] { interfaceImpl },
//					new MyInvocationHandler(sqlSession.getMapper(interfaceImpl)));
            Object instance = interfaceImpl.newInstance();
            Method method = instance.getClass().getMethod("findAllXml");

            System.out.println("before");
            method.invoke(instance);
            System.out.println(method.invoke(instance));
            System.out.println("after");
            map.put("pageValue", method.invoke(instance));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /***************/

//		// 第一个参数是被调用方法的名称，后面接着这个方法的形参类型
//		Method setFunc = class1.getDeclaredMethod("findAllXml", EntityWrapper.class);
//		Method toStringFunc = class1.getDeclaredMethod("toString");
//		// 取得方法后即可通过invoke方法调用，传入被调用方法所在类的对象和实参,对象可以通过cls.newInstance取得
//		setFunc.invoke(class1.newInstance(), wrapper);
//		System.out.println(toStringFunc.invoke(class1.newInstance(), wrapper));

        return map;
    }
}