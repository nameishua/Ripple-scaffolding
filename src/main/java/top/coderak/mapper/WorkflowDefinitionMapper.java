package top.coderak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.coderak.entity.WorkflowDefinition;

import java.util.List;

@Mapper
public interface WorkflowDefinitionMapper {

    List<WorkflowDefinition> selectAll();

    List<WorkflowDefinition> selectPublished();

    WorkflowDefinition selectById(@Param("id") String id);

    WorkflowDefinition selectByCode(@Param("code") String code);

    int insert(WorkflowDefinition entity);

    int updateById(WorkflowDefinition entity);
}
