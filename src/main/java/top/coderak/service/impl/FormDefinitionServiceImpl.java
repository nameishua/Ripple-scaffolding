package top.coderak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.entity.FormDefinition;
import top.coderak.mapper.FormDefinitionMapper;
import top.coderak.service.FormDefinitionService;

import java.util.Date;
import java.util.List;

@Service
public class FormDefinitionServiceImpl implements FormDefinitionService {

    @Autowired
    private FormDefinitionMapper formDefinitionMapper;

    @Override
    public List<FormDefinition> listAll() {
        return formDefinitionMapper.selectAll();
    }

    @Override
    public FormDefinition getById(String id) {
        return formDefinitionMapper.selectById(id);
    }

    @Override
    public FormDefinition getByCode(String code) {
        return formDefinitionMapper.selectByCode(code);
    }

    @Override
    public boolean add(FormDefinition form, String operator) {
        form.setId(GenerateSequenceUtil.generateSequenceNo());
        form.setVersion(form.getVersion() == null ? 1 : form.getVersion());
        form.setStatus(form.getStatus() == null ? "draft" : form.getStatus());
        form.setFlag("启用");
        form.setCreateBy(operator);
        form.setUpdateBy(operator);
        form.setCreateDate(new Date());
        form.setUpdateDate(new Date());
        return formDefinitionMapper.insert(form) > 0;
    }

    @Override
    public boolean update(FormDefinition form, String operator) {
        form.setUpdateBy(operator);
        form.setUpdateDate(new Date());
        return formDefinitionMapper.updateById(form) > 0;
    }

    @Override
    public boolean delete(String id, String operator) {
        FormDefinition form = formDefinitionMapper.selectById(id);
        if (form == null) {
            return false;
        }
        form.setFlag("删除");
        form.setUpdateBy(operator);
        form.setUpdateDate(new Date());
        return formDefinitionMapper.updateById(form) > 0;
    }

    @Override
    public boolean publish(String id, String operator) {
        FormDefinition form = formDefinitionMapper.selectById(id);
        if (form == null) {
            return false;
        }
        form.setStatus("published");
        form.setUpdateBy(operator);
        form.setUpdateDate(new Date());
        return formDefinitionMapper.updateById(form) > 0;
    }
}
