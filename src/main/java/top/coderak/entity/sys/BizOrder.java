package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class BizOrder extends BaseBean {
    private String id;
    private String orderNo;
    private String customerName;
    private String productName;
    private BigDecimal amount;
    private String status;
}
