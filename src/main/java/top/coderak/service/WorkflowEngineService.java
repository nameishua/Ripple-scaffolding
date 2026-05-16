package top.coderak.service;

import top.coderak.entity.WorkflowInstance;
import top.coderak.entity.WorkflowTask;

import java.util.List;

public interface WorkflowEngineService {

    WorkflowInstance start(String workflowCode, String formDataId, String businessKey, String starter);

    boolean approve(String taskId, String comment, String operator);

    boolean reject(String taskId, String comment, String operator);

    List<WorkflowInstance> listInstances();

    List<WorkflowInstance> listMyInstances(String starter);

    List<WorkflowTask> listTodo(String assignee);

    List<WorkflowTask> listTasksByInstance(String instanceId);
}
