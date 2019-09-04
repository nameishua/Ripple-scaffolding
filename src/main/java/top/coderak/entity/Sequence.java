package top.coderak.entity;

import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;
import lombok.Data;

@Data
@Table(name = "sequence")
public class Sequence extends BaseBean {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String id;

    @Column(name = "sequence", type = MySqlTypeConstant.INTEGER, length = 10)
    private int sequence;

    @Column(name = "type", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String type;
}
