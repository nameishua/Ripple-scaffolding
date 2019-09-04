package top.coderak.core.base.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import top.coderak.core.base.constants.MySqlTypeConstant;
import top.coderak.core.base.sqlTool.Column;
import lombok.Data;

import java.util.Date;

@Data
public class BaseBean {

    /**
     * 创建者
     */
    @Column(name = "create_by", type = MySqlTypeConstant.VARCHAR, length = 255)
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间
     */
    @Column(name = "create_date", type = MySqlTypeConstant.DATETIME, length = 0)
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 更新者
     */
    @Column(name = "update_by", type = MySqlTypeConstant.VARCHAR, length = 255)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    /**
     * 更新时间
     */
    @Column(name = "update_date", type = MySqlTypeConstant.DATETIME, length = 0)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 标记
     */
    @Column(name = "flag", type = MySqlTypeConstant.VARCHAR, length = 255)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String flag;
}
