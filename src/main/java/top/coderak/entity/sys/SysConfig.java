package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseBean {
    private String id;
    private String configKey;
    private String configValue;
    private String name;
    private String remark;
}
