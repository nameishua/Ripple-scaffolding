package top.coderak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.coderak.entity.WorkflowInstance;

import java.util.List;

@Mapper
public interface WorkflowInstanceMapper {

    List<WorkflowInstance> selectAll();

    List<WorkflowInstance> selectByStarter(@Param("starter") String starter);

    WorkflowInstance selectById(@Param("id") String id);

    int insert(WorkflowInstance entity);

    int updateById(WorkflowInstance entity);
}
