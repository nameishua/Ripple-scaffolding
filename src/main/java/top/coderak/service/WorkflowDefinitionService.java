package top.coderak.service;

import top.coderak.entity.WorkflowDefinition;

import java.util.List;

public interface WorkflowDefinitionService {

    List<WorkflowDefinition> listAll();

    List<WorkflowDefinition> listPublished();

    WorkflowDefinition getById(String id);

    WorkflowDefinition getByCode(String code);

    boolean add(WorkflowDefinition def, String operator);

    boolean update(WorkflowDefinition def, String operator);

    boolean delete(String id, String operator);

    boolean publish(String id, String operator);
}
