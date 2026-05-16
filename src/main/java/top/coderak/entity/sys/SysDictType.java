package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictType extends BaseBean {
    private String id;
    private String name;
    private String code;
    private String status;
    private String remark;
}
