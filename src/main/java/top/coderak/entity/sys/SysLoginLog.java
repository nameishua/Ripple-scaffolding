package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysLoginLog extends BaseBean {
    private String id;
    private String account;
    private String loginIp;
    private String location;
    private String browser;
    private String status;
    private String message;
    private Date loginTime;
}
