package top.coderak.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rp_form_data")
public class FormData extends BaseBean {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false, isKey = true)
    private String id;

    @Column(name = "form_code", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false)
    private String formCode;

    @Column(name = "business_key", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String businessKey;

    @Column(name = "data_json", type = MySqlTypeConstant.TEXT)
    private String dataJson;

    @Column(name = "status", type = MySqlTypeConstant.VARCHAR, length = 20)
    private String status;

    @Column(name = "submitter", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String submitter;
}
