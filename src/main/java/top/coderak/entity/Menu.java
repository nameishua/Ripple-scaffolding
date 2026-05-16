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
@Table(name = "rp_menu")
public class Menu extends BaseBean {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false, isKey = true, isAutoIncrement = false)
    private String id;

    @Column(name = "parent_id", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String parentId;

    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 100, isNull = false)
    private String name;

    @Column(name = "path", type = MySqlTypeConstant.VARCHAR, length = 200)
    private String path;

    @Column(name = "component", type = MySqlTypeConstant.VARCHAR, length = 200)
    private String component;

    @Column(name = "icon", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String icon;

    @Column(name = "sort_no", type = MySqlTypeConstant.INTEGER, length = 10)
    private Integer sortNo;

    @Column(name = "type", type = MySqlTypeConstant.VARCHAR, length = 20)
    private String type;

    @Column(name = "permission", type = MySqlTypeConstant.VARCHAR, length = 100)
    private String permission;

    private List<Menu> children;
}
