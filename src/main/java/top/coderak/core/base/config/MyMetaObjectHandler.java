package top.coderak.core.base.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import top.coderak.core.base.constants.BaseConstants;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import top.coderak.core.base.constants.BaseConstants;

import java.util.Date;


/**
 * mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 */
@Component
public class MyMetaObjectHandler extends MetaObjectHandler {

    /**
     * 插入填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        // 获取公共字段的值
        Object createBy = this.getFieldValByName("createBy", metaObject);

        Object createDate = this.getFieldValByName("createDate", metaObject);

        Object updateBy = this.getFieldValByName("updateBy", metaObject);

        Object updateDate = this.getFieldValByName("updateDate", metaObject);

        Object flag = this.getFieldValByName("flag", metaObject);

        //获取当前登录用户
        String accountString = SecurityUtils.getSubject().getPrincipal().toString();

        System.out.println(accountString);

        if (null == createBy) {

            System.err.println("====满足填充条件====");

            this.setFieldValByName("createBy", accountString, metaObject);
        }

        if (null == createDate) {

            System.err.println("====满足填充条件====");

            this.setFieldValByName("createDate", new Date(), metaObject);
        }

        if (null == flag) {

            System.err.println("====满足填充条件====");

            this.setFieldValByName("flag", BaseConstants.FLAG_CREATE, metaObject);
        }

        if (null == updateBy) {

            System.err.println("====满足填充条件====");

            this.setFieldValByName("updateBy", accountString, metaObject);
        }

        if (null == updateDate) {

            System.err.println("====满足填充条件====");

            this.setFieldValByName("updateDate", new Date(), metaObject);
        }

        updateFill(metaObject);
    }

    /**
     * 修改填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        // 获取公共字段的值
        Object updateBy = this.getFieldValByName("updateBy", metaObject);

        Object updateDate = this.getFieldValByName("updateDate", metaObject);

        Object flag = this.getFieldValByName("flag", metaObject);

        System.out.println(updateBy);

        System.out.println(updateDate);

        //获取当前登录用户
        String accountString = SecurityUtils.getSubject().getPrincipal().toString();

        System.out.println(accountString);

        if (null == updateBy) {

            System.err.println("====满足填充条件====");

            this.setFieldValByName("updateBy", accountString, metaObject);
        }

        if (null == updateDate) {

            System.err.println("====满足填充条件====");

            this.setFieldValByName("updateDate", new Date(), metaObject);
        }

        if (null == flag) {

            System.err.println("====满足填充条件====");

            this.setFieldValByName("flag", BaseConstants.FLAG_UPDATE, metaObject);
        }
    }

}