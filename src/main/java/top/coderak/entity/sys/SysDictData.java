package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends BaseBean {
    private String id;
    private String dictCode;
    private String label;
    private String value;
    private Integer sortNo;
    private String status;
}
