package top.coderak.service.support;

import top.coderak.core.base.bean.BaseBean;
import top.coderak.core.utils.GenerateSequenceUtil;

import java.util.Date;

public final class FlagCrudHelper {

    private FlagCrudHelper() {
    }

    public static String newId() {
        return GenerateSequenceUtil.generateSequenceNo();
    }

    public static void onCreate(BaseBean entity, String operator) {
        entity.setFlag("启用");
        entity.setCreateBy(operator);
        entity.setUpdateBy(operator);
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
    }

    public static void onUpdate(BaseBean entity, String operator) {
        entity.setUpdateBy(operator);
        entity.setUpdateDate(new Date());
    }

    public static void onDelete(BaseBean entity, String operator) {
        entity.setFlag("删除");
        entity.setUpdateBy(operator);
        entity.setUpdateDate(new Date());
    }
}
