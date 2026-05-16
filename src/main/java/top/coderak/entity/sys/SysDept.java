package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseBean {
    private String id;
    private String parentId;
    private String name;
    private String leader;
    private String phone;
    private Integer sortNo;
}
