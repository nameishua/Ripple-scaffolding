package top.coderak.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.entity.WorkflowDefinition;
import top.coderak.entity.WorkflowInstance;
import top.coderak.entity.WorkflowTask;
import top.coderak.mapper.WorkflowInstanceMapper;
import top.coderak.mapper.WorkflowTaskMapper;
import top.coderak.modules.workflow.model.WfDefinitionModel;
import top.coderak.modules.workflow.model.WfEdge;
import top.coderak.modules.workflow.model.WfNode;
import top.coderak.service.WorkflowDefinitionService;
import top.coderak.service.WorkflowEngineService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkflowEngineServiceImpl implements WorkflowEngineService {

    @Autowired
    private WorkflowDefinitionService workflowDefinitionService;

    @Autowired
    private WorkflowInstanceMapper workflowInstanceMapper;

    @Autowired
    private WorkflowTaskMapper workflowTaskMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public WorkflowInstance start(String workflowCode, String formDataId, String businessKey, String starter) {
        WorkflowDefinition def = workflowDefinitionService.getByCode(workflowCode);
        if (def == null || !"published".equals(def.getStatus())) {
            throw new IllegalArgumentException("Workflow not found or not published: " + workflowCode);
        }
        WfDefinitionModel model = parseDefinition(def.getDefinitionJson());
        WfNode firstApproval = findFirstApprovalNode(model);
        if (firstApproval == null) {
            throw new IllegalStateException("Workflow has no approval node");
        }

        WorkflowInstance instance = new WorkflowInstance();
        instance.setId(GenerateSequenceUtil.generateSequenceNo());
        instance.setWorkflowCode(workflowCode);
        instance.setBusinessKey(businessKey != null ? businessKey : GenerateSequenceUtil.generateSequenceNo());
        instance.setFormDataId(formDataId);
        instance.setCurrentNode(firstApproval.getKey());
        instance.setStatus("running");
        instance.setStarter(starter);
        instance.setFlag("启用");
        instance.setCreateBy(starter);
        instance.setUpdateBy(starter);
        instance.setCreateDate(new Date());
        instance.setUpdateDate(new Date());
        workflowInstanceMapper.insert(instance);

        createPendingTask(instance.getId(), firstApproval, starter);
        return instance;
    }

    @Override
    @Transactional
    public boolean approve(String taskId, String comment, String operator) {
        return completeTask(taskId, "approve", comment, operator);
    }

    @Override
    @Transactional
    public boolean reject(String taskId, String comment, String operator) {
        return completeTask(taskId, "reject", comment, operator);
    }

    private boolean completeTask(String taskId, String action, String comment, String operator) {
        WorkflowTask task = workflowTaskMapper.selectById(taskId);
        if (task == null || !"pending".equals(task.getStatus())) {
            return false;
        }
        WorkflowInstance instance = workflowInstanceMapper.selectById(task.getInstanceId());
        if (instance == null || !"running".equals(instance.getStatus())) {
            return false;
        }
        WorkflowDefinition def = workflowDefinitionService.getByCode(instance.getWorkflowCode());
        WfDefinitionModel model = parseDefinition(def.getDefinitionJson());

        task.setStatus("completed");
        task.setAction(action);
        task.setComment(comment);
        task.setCompletedDate(new Date());
        task.setUpdateBy(operator);
        task.setUpdateDate(new Date());
        workflowTaskMapper.updateById(task);

        String nextKey = resolveNextNode(model, task.getNodeKey(), action);
        WfNode nextNode = findNode(model, nextKey);
        if (nextNode == null || "end".equals(nextNode.getType())) {
            instance.setStatus("approve".equals(action) ? "completed" : "rejected");
            instance.setCurrentNode(nextKey != null ? nextKey : "end");
        } else if ("approval".equals(nextNode.getType())) {
            instance.setCurrentNode(nextNode.getKey());
            String assignee = nextNode.getAssignee() != null ? nextNode.getAssignee() : operator;
            createPendingTask(instance.getId(), nextNode, assignee);
        } else {
            instance.setStatus("completed");
            instance.setCurrentNode(nextNode.getKey());
        }
        instance.setUpdateBy(operator);
        instance.setUpdateDate(new Date());
        workflowInstanceMapper.updateById(instance);
        return true;
    }

    private void createPendingTask(String instanceId, WfNode node, String assignee) {
        WorkflowTask task = new WorkflowTask();
        task.setId(GenerateSequenceUtil.generateSequenceNo());
        task.setInstanceId(instanceId);
        task.setNodeKey(node.getKey());
        task.setNodeName(node.getName());
        task.setAssignee(assignee);
        task.setStatus("pending");
        task.setFlag("启用");
        task.setCreateBy(assignee);
        task.setUpdateBy(assignee);
        task.setCreateDate(new Date());
        task.setUpdateDate(new Date());
        workflowTaskMapper.insert(task);
    }

    private WfDefinitionModel parseDefinition(String json) {
        try {
            return objectMapper.readValue(json, WfDefinitionModel.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid workflow definition JSON", e);
        }
    }

    private WfNode findFirstApprovalNode(WfDefinitionModel model) {
        if (model.getNodes() == null) {
            return null;
        }
        Optional<WfNode> start = model.getNodes().stream().filter(n -> "start".equals(n.getType())).findFirst();
        if (!start.isPresent() || model.getEdges() == null) {
            return model.getNodes().stream().filter(n -> "approval".equals(n.getType())).findFirst().orElse(null);
        }
        String startKey = start.get().getKey();
        for (WfEdge edge : model.getEdges()) {
            if (startKey.equals(edge.getFrom())) {
                WfNode target = findNode(model, edge.getTo());
                if (target != null && "approval".equals(target.getType())) {
                    return target;
                }
            }
        }
        return model.getNodes().stream().filter(n -> "approval".equals(n.getType())).findFirst().orElse(null);
    }

    private String resolveNextNode(WfDefinitionModel model, String fromKey, String action) {
        if (model.getEdges() == null) {
            return "end";
        }
        for (WfEdge edge : model.getEdges()) {
            if (fromKey.equals(edge.getFrom())) {
                if (edge.getOn() == null || edge.getOn().isEmpty() || action.equals(edge.getOn())) {
                    return edge.getTo();
                }
            }
        }
        return "end";
    }

    private WfNode findNode(WfDefinitionModel model, String key) {
        if (model.getNodes() == null || key == null) {
            return null;
        }
        return model.getNodes().stream().filter(n -> key.equals(n.getKey())).findFirst().orElse(null);
    }

    @Override
    public List<WorkflowInstance> listInstances() {
        return workflowInstanceMapper.selectAll();
    }

    @Override
    public List<WorkflowInstance> listMyInstances(String starter) {
        return workflowInstanceMapper.selectByStarter(starter);
    }

    @Override
    public List<WorkflowTask> listTodo(String assignee) {
        return workflowTaskMapper.selectTodoByAssignee(assignee);
    }

    @Override
    public List<WorkflowTask> listTasksByInstance(String instanceId) {
        return workflowTaskMapper.selectByInstanceId(instanceId);
    }
}
