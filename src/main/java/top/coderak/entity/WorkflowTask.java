package top.coderak.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import top.coderak.core.base.sqlTool.Table;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rp_workflow_task")
public class WorkflowTask extends BaseBean {

    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false, isKey = true)
    private String id;

    @Column(name = "instance_id", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false)
    private String instanceId;

    @Column(name = "node_key", type = MySqlTypeConstant.VARCHAR, length = 64, isNull = false)
    private String nodeKey;

    @Column(name = "node_name", type = MySqlTypeConstant.VARCHAR, length = 100)
    private String nodeName;

    @Column(name = "assignee", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String assignee;

    @Column(name = "status", type = MySqlTypeConstant.VARCHAR, length = 20)
    private String status;

    @Column(name = "action", type = MySqlTypeConstant.VARCHAR, length = 20)
    private String action;

    @Column(name = "comment", type = MySqlTypeConstant.VARCHAR, length = 500)
    private String comment;

    @Column(name = "completed_date", type = MySqlTypeConstant.DATETIME)
    private Date completedDate;

    private String workflowCode;
    private String workflowName;
    private String formDataId;
}
