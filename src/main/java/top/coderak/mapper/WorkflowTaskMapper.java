package top.coderak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.coderak.entity.WorkflowTask;

import java.util.List;

@Mapper
public interface WorkflowTaskMapper {

    List<WorkflowTask> selectByInstanceId(@Param("instanceId") String instanceId);

    List<WorkflowTask> selectTodoByAssignee(@Param("assignee") String assignee);

    List<WorkflowTask> selectAll();

    WorkflowTask selectById(@Param("id") String id);

    int insert(WorkflowTask entity);

    int updateById(WorkflowTask entity);
}
