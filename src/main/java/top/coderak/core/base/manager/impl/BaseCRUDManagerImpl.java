package top.coderak.core.base.manager.impl;

import com.alibaba.fastjson.JSONObject;
import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.manager.BaseCRUDManager;
import top.coderak.core.base.mapper.BaseCRUDMapper;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.manager.BaseCRUDManager;
import top.coderak.core.base.mapper.BaseCRUDMapper;

import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("ALL")
@Transactional
@Service("BaseCRUDManager")
public class BaseCRUDManagerImpl<T> implements BaseCRUDManager<T> {

    private static final Logger log = LoggerFactory.getLogger(BaseCRUDManagerImpl.class);

    private static final String KEYFIELDMAP = "keyFieldMap";

    @Autowired
    private BaseCRUDMapper baseMysqlCRUDMapper;

    public void save(Object obj) {

        boolean isSave = true;

        Table tableName = obj.getClass().getAnnotation(Table.class);

        if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {

            log.error("必须使用model中的对象！");

            return;
        }

        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();

        Map<Object, Object> dataMap = new HashMap<Object, Object>();

        Map<String, Object> keyFieldMap = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();

        for (Field field : declaredFields) {

            try {

                // 私有属性需要设置访问权限
                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);

                if (column == null) {

                    log.info("该field没有配置注解不是表中在字段！");

                    continue;
                }

                // 如果是主键，并且不是空的时候，这时候应该是更新操作
                if (column.isKey() && field.get(obj) != null) {

                    isSave = false;

                    keyFieldMap.put(field.getName(), field.get(obj));
                }

                // 如果是自增,并且是保存的场合，不需要添加到map中做保存
                if (isSave && column.isAutoIncrement()) {

                    log.info("字段：" + field.getName() + "是自增的不需要添加到map中");

                    continue;
                }

                dataMap.put(field.getName(), field.get(obj));
            } catch (IllegalArgumentException e) {

                e.printStackTrace();
            } catch (IllegalAccessException e) {

                e.printStackTrace();
            }
        }

        if (isSave) {

            tableMap.put(tableName.name(), dataMap);
            // 执行保存操作

            baseMysqlCRUDMapper.save(tableMap);
        } else {

            dataMap.put(KEYFIELDMAP, keyFieldMap);

            tableMap.put(tableName.name(), dataMap);

            // 执行更新操作根据主键
            baseMysqlCRUDMapper.update(tableMap);
        }
    }

    @SuppressWarnings("null")
    public void delete(Object obj) {

        // 得到表名
        Table tableName = obj.getClass().getAnnotation(Table.class);

        if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {

            log.error("必须使用model中的对象！");

            return;
        }

        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();

        Map<Object, Object> dataMap = new HashMap<Object, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();

        for (Field field : declaredFields) {

            // 设置访问权限
            field.setAccessible(true);

            // 得到字段的配置
            Column column = field.getAnnotation(Column.class);

            if (column != null) {

                log.info("该field没有配置注解不是表中在字段！");

                continue;
            }

            try {

                dataMap.put(column.name(), field.get(obj));
            } catch (IllegalArgumentException e) {

                e.printStackTrace();
            } catch (IllegalAccessException e) {

                e.printStackTrace();
            }
        }

        tableMap.put(tableName.name(), dataMap);

        baseMysqlCRUDMapper.delete(tableMap);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List query(Object obj) {

        System.out.println("进来执行");

        // 得到表名
        Table tableName = obj.getClass().getAnnotation(Table.class);

        if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {

            log.error("必须使用model中的对象！");

            return null;
        }

        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();

        Map<Object, Object> dataMap = new HashMap<Object, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();

        Object baseBean = new BaseBean();

        Field[] baseDeclaredFields = baseBean.getClass().getDeclaredFields();

        System.out.println(declaredFields);

        List<Field> fieldList = new ArrayList<Field>();

        Collections.addAll(fieldList, declaredFields);

        Collections.addAll(fieldList, baseDeclaredFields);

        Field[] fields = new Field[fieldList.size()];

        fieldList.toArray(fields);

        for (Field field : fields) {

            System.out.println(field);

            // 设置访问权限
            field.setAccessible(true);

            // 得到字段的配置
            Column column = field.getAnnotation(Column.class);

            System.out.println(column);

            if (column == null) {

                log.info("该field没有配置注解不是表中在字段！");

                continue;
            }

            if ("create_by".equals(column.name()) || "create_date".equals(column.name()) || "update_by".equals(column.name()) || "update_date".equals(column.name()) || "flag".equals(column.name())) {

                System.out.println("base");

                try {

                    dataMap.put(column.name(), field.get(baseBean));
                } catch (IllegalArgumentException e) {

                    e.printStackTrace();
                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                }
            } else {

                System.out.println("unit");

                try {

                    dataMap.put(column.name(), field.get(obj));
                } catch (IllegalArgumentException e) {

                    e.printStackTrace();
                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                }
            }

            System.out.println(dataMap);
        }

        System.out.println(dataMap);

        tableMap.put(tableName.name(), dataMap);

        System.out.println("tableMap:" + tableMap);

        List<Map<String, Object>> query = baseMysqlCRUDMapper.query(tableMap);

        System.out.println("query:" + query);

        List<Object> list = new ArrayList<Object>();

        for (Map<String, Object> map : query) {

            System.out.println("map:" + map);

            JSONObject paramsObj = new JSONObject(map);

            System.out.println("paramsObj:" + paramsObj);

            list.add(paramsObj);

        }

        return list;
    }

}
