package top.coderak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.entity.WorkflowDefinition;
import top.coderak.mapper.WorkflowDefinitionMapper;
import top.coderak.service.WorkflowDefinitionService;

import java.util.Date;
import java.util.List;

@Service
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {

    @Autowired
    private WorkflowDefinitionMapper workflowDefinitionMapper;

    @Override
    public List<WorkflowDefinition> listAll() {
        return workflowDefinitionMapper.selectAll();
    }

    @Override
    public List<WorkflowDefinition> listPublished() {
        return workflowDefinitionMapper.selectPublished();
    }

    @Override
    public WorkflowDefinition getById(String id) {
        return workflowDefinitionMapper.selectById(id);
    }

    @Override
    public WorkflowDefinition getByCode(String code) {
        return workflowDefinitionMapper.selectByCode(code);
    }

    @Override
    public boolean add(WorkflowDefinition def, String operator) {
        def.setId(GenerateSequenceUtil.generateSequenceNo());
        def.setVersion(def.getVersion() == null ? 1 : def.getVersion());
        def.setStatus(def.getStatus() == null ? "draft" : def.getStatus());
        def.setFlag("启用");
        def.setCreateBy(operator);
        def.setUpdateBy(operator);
        def.setCreateDate(new Date());
        def.setUpdateDate(new Date());
        return workflowDefinitionMapper.insert(def) > 0;
    }

    @Override
    public boolean update(WorkflowDefinition def, String operator) {
        def.setUpdateBy(operator);
        def.setUpdateDate(new Date());
        return workflowDefinitionMapper.updateById(def) > 0;
    }

    @Override
    public boolean delete(String id, String operator) {
        WorkflowDefinition def = workflowDefinitionMapper.selectById(id);
        if (def == null) {
            return false;
        }
        def.setFlag("删除");
        def.setUpdateBy(operator);
        def.setUpdateDate(new Date());
        return workflowDefinitionMapper.updateById(def) > 0;
    }

    @Override
    public boolean publish(String id, String operator) {
        WorkflowDefinition def = workflowDefinitionMapper.selectById(id);
        if (def == null) {
            return false;
        }
        def.setStatus("published");
        def.setUpdateBy(operator);
        def.setUpdateDate(new Date());
        return workflowDefinitionMapper.updateById(def) > 0;
    }
}
