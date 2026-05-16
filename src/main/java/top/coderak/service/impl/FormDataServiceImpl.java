package top.coderak.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.coderak.core.utils.GenerateSequenceUtil;
import top.coderak.entity.FormData;
import top.coderak.entity.FormDefinition;
import top.coderak.mapper.FormDataMapper;
import top.coderak.service.FormDataService;
import top.coderak.service.FormDefinitionService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FormDataServiceImpl implements FormDataService {

    @Autowired
    private FormDataMapper formDataMapper;

    @Autowired
    private FormDefinitionService formDefinitionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<FormData> listAll() {
        return formDataMapper.selectAll();
    }

    @Override
    public List<FormData> listByFormCode(String formCode) {
        return formDataMapper.selectByFormCode(formCode);
    }

    @Override
    public FormData getById(String id) {
        return formDataMapper.selectById(id);
    }

    @Override
    public FormData submit(String formCode, Map<String, Object> data, String submitter) {
        FormDefinition def = formDefinitionService.getByCode(formCode);
        if (def == null || !"published".equals(def.getStatus())) {
            throw new IllegalArgumentException("Form not found or not published: " + formCode);
        }
        FormData record = new FormData();
        record.setId(GenerateSequenceUtil.generateSequenceNo());
        record.setFormCode(formCode);
        record.setBusinessKey(GenerateSequenceUtil.generateSequenceNo());
        try {
            record.setDataJson(objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid form data", e);
        }
        record.setStatus("submitted");
        record.setSubmitter(submitter);
        record.setFlag("启用");
        record.setCreateBy(submitter);
        record.setUpdateBy(submitter);
        record.setCreateDate(new Date());
        record.setUpdateDate(new Date());
        formDataMapper.insert(record);
        return record;
    }
}
