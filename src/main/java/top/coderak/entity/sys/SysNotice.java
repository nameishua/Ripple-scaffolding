package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends BaseBean {
    private String id;
    private String title;
    private String content;
    private String noticeType;
    private String status;
    private Date publishTime;
}
