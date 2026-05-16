package top.coderak.service;

import top.coderak.entity.FormData;

import java.util.List;
import java.util.Map;

public interface FormDataService {

    List<FormData> listAll();

    List<FormData> listByFormCode(String formCode);

    FormData getById(String id);

    FormData submit(String formCode, Map<String, Object> data, String submitter);
}
