package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperLog extends BaseBean {
    private String id;
    private String module;
    private String action;
    private String method;
    private String requestUrl;
    private String operator;
    private String operIp;
    private String status;
    private String detail;
    private Date operTime;
}
