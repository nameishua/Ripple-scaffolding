package top.coderak.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rp_permission")
public class Permission extends BaseBean {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false, isKey = true, isAutoIncrement = false)
    private String id;

    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 100, isNull = false)
    private String name;

    @Column(name = "code", type = MySqlTypeConstant.VARCHAR, length = 50, isNull = false)
    private String code;

    @Column(name = "type", type = MySqlTypeConstant.VARCHAR, length = 20)
    private String type;

    @Column(name = "description", type = MySqlTypeConstant.VARCHAR, length = 200)
    private String description;
}
