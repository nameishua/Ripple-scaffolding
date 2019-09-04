package top.coderak.entity;

import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;
import lombok.Data;

/**
 * 用户pojo类
 *
 * @author zyh
 * @date 2019/7/21 0021
 */
@Data
@Table(name = "user")
public class User extends BaseBean {

    /**
     * 主键
     */
    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false, isKey = true, isAutoIncrement = false)
    private String id;
    /**
     * 姓名
     */
    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String name;

    /**
     * 年龄
     */
    @Column(name = "age", type = MySqlTypeConstant.INTEGER, length = 64)
    private int age;

    /**
     * 密码
     */
    @Column(name = "password", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String password;

    /**
     * 账号
     */
    @Column(name = "account", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String account;
    /**
     * 排序号
     */
    @Column(name = "sort", type = MySqlTypeConstant.INTEGER, length = 64)
    private Integer sort;
    /**
     * 编码
     */
    @Column(name = "code", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;

    public String show() {
        System.out.println("反射测试一下");
        return "ok";
    }
}
