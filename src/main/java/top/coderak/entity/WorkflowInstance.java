package top.coderak.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rp_workflow_instance")
public class WorkflowInstance extends BaseBean {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false, isKey = true)
    private String id;

    @Column(name = "workflow_code", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false)
    private String workflowCode;

    @Column(name = "business_key", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String businessKey;

    @Column(name = "form_data_id", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String formDataId;

    @Column(name = "current_node", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String currentNode;

    @Column(name = "status", type = MySqlTypeConstant.VARCHAR, length = 20)
    private String status;

    @Column(name = "starter", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String starter;
}
