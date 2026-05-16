package top.coderak.service;

import top.coderak.entity.FormDefinition;

import java.util.List;

public interface FormDefinitionService {

    List<FormDefinition> listAll();

    FormDefinition getById(String id);

    FormDefinition getByCode(String code);

    boolean add(FormDefinition form, String operator);

    boolean update(FormDefinition form, String operator);

    boolean delete(String id, String operator);

    boolean publish(String id, String operator);
}
