package top.coderak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.coderak.entity.FormDefinition;

import java.util.List;

@Mapper
public interface FormDefinitionMapper {

    List<FormDefinition> selectAll();

    FormDefinition selectById(@Param("id") String id);

    FormDefinition selectByCode(@Param("code") String code);

    int insert(FormDefinition entity);

    int updateById(FormDefinition entity);
}
