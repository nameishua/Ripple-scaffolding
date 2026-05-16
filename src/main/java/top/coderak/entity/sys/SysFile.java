package top.coderak.entity.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.coderak.core.base.bean.BaseBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysFile extends BaseBean {
    private String id;
    private String fileName;
    private String originalName;
    private String filePath;
    private Long fileSize;
    private String mimeType;
}
