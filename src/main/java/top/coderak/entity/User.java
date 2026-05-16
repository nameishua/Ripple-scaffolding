package top.coderak.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "user")
public class User extends BaseBean {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false, isKey = true, isAutoIncrement = false)
    private String id;

    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String name;

    @Column(name = "age", type = MySqlTypeConstant.INTEGER, length = 64)
    private int age;

    @Column(name = "password", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String password;

    @Column(name = "account", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String account;

    @Column(name = "sort", type = MySqlTypeConstant.INTEGER, length = 64)
    private Integer sort;

    @Column(name = "code", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;

    private List<String> roleIds;
    private List<Role> roles;
}
