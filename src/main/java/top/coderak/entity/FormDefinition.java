package top.coderak.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rp_form_definition")
public class FormDefinition extends BaseBean {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false, isKey = true)
    private String id;

    @Column(name = "code", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false)
    private String code;

    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, length = 100, isNull = false)
    private String name;

    @Column(name = "description", type = MySqlTypeConstant.VARCHAR, length = 500)
    private String description;

    @Column(name = "schema_json", type = MySqlTypeConstant.TEXT)
    private String schemaJson;

    @Column(name = "version", type = MySqlTypeConstant.INT)
    private Integer version;

    @Column(name = "status", type = MySqlTypeConstant.VARCHAR, length = 20)
    private String status;
}
