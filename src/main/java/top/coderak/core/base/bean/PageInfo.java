package top.coderak.core.base.bean;

import lombok.Data;

@Data
public class PageInfo {

    /**
     * pageInfo 页数信息
     */
    private Object pageInfo;

    /**
     * pageValue 查询结果
     */
    private Object pageValue;

}
