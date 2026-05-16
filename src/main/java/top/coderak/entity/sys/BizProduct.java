package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class BizProduct extends BaseBean {
    private String id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String status;
}
