package top.coderak.core.base.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/*
 * 功能描述
 * @author zyh
 * @date 2019/7/22 0022
 */
@Mapper
public interface BaseCRUDMapper {

    /**
     * 保存
     *
     * @param tableMap
     */
    public void save(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);

    /**
     * 更新
     *
     * @param tableMap
     */
    public void update(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);

    /**
     * 删除
     *
     * @param tableMap
     */
    public void delete(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);

    /**
     * 查询
     *
     * @param tableMap
     */
    public List<Map<String, Object>> query(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);


}
