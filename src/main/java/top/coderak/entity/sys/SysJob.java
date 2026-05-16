package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysJob extends BaseBean {
    private String id;
    private String jobName;
    private String jobGroup;
    private String cronExpression;
    private String beanName;
    private String status;
    private String remark;
}
