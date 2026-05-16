package top.coderak.modules.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.coderak.core.api.ApiResult;
import top.coderak.core.utils.JWTUtils;
import top.coderak.entity.FormData;
import top.coderak.entity.FormDefinition;
import top.coderak.service.FormDataService;
import top.coderak.service.FormDefinitionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "动态表单管理")
@RestController
@RequestMapping("/admin/form")
public class FormAdminController {

    @Autowired
    private FormDefinitionService formDefinitionService;

    @Autowired
    private FormDataService formDataService;

    private String getAccount(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return JWTUtils.getAccount(authHeader.substring(7));
        }
        return null;
    }

    @ApiOperation("表单定义列表")
    @GetMapping("/definition/list")
    public ApiResult<List<FormDefinition>> listDefinitions() {
        return ApiResult.ok(formDefinitionService.listAll());
    }

    @ApiOperation("已发布表单定义")
    @GetMapping("/definition/published")
    public ApiResult<List<FormDefinition>> listPublishedDefinitions() {
        return ApiResult.ok(formDefinitionService.listAll().stream()
                .filter(f -> "published".equals(f.getStatus()))
                .collect(java.util.stream.Collectors.toList()));
    }

    @ApiOperation("表单定义详情")
    @GetMapping("/definition/{id}")
    public ApiResult<FormDefinition> getDefinition(@PathVariable String id) {
        return ApiResult.ok(formDefinitionService.getById(id));
    }

    @ApiOperation("按编码获取表单定义")
    @GetMapping("/definition/code/{code}")
    public ApiResult<FormDefinition> getDefinitionByCode(@PathVariable String code) {
        return ApiResult.ok(formDefinitionService.getByCode(code));
    }

    @ApiOperation("新增表单定义")
    @PostMapping("/definition/add")
    public ApiResult<Void> addDefinition(@RequestBody FormDefinition form, HttpServletRequest request) {
        String account = getAccount(request);
        return formDefinitionService.add(form, account) ? ApiResult.ok() : ApiResult.fail("Add failed");
    }

    @ApiOperation("更新表单定义")
    @PostMapping("/definition/update")
    public ApiResult<Void> updateDefinition(@RequestBody FormDefinition form, HttpServletRequest request) {
        String account = getAccount(request);
        return formDefinitionService.update(form, account) ? ApiResult.ok() : ApiResult.fail("Update failed");
    }

    @ApiOperation("删除表单定义")
    @PostMapping("/definition/delete")
    public ApiResult<Void> deleteDefinition(@RequestParam String id, HttpServletRequest request) {
        String account = getAccount(request);
        return formDefinitionService.delete(id, account) ? ApiResult.ok() : ApiResult.fail("Delete failed");
    }

    @ApiOperation("发布表单定义")
    @PostMapping("/definition/publish")
    public ApiResult<Void> publishDefinition(@RequestParam String id, HttpServletRequest request) {
        String account = getAccount(request);
        return formDefinitionService.publish(id, account) ? ApiResult.ok() : ApiResult.fail("Publish failed");
    }

    @ApiOperation("表单数据列表")
    @GetMapping("/data/list")
    public ApiResult<List<FormData>> listData(
            @ApiParam("表单编码") @RequestParam(required = false) String formCode) {
        if (formCode != null && !formCode.isEmpty()) {
            return ApiResult.ok(formDataService.listByFormCode(formCode));
        }
        return ApiResult.ok(formDataService.listAll());
    }

    @ApiOperation("表单数据详情")
    @GetMapping("/data/{id}")
    public ApiResult<FormData> getData(@PathVariable String id) {
        return ApiResult.ok(formDataService.getById(id));
    }

    @ApiOperation("提交表单数据")
    @PostMapping("/data/submit")
    public ApiResult<FormData> submitData(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        String account = getAccount(request);
        if (account == null) {
            return ApiResult.fail("Not logged in");
        }
        String formCode = (String) body.get("formCode");
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        if (formCode == null || data == null) {
            return ApiResult.fail("formCode and data are required");
        }
        try {
            return ApiResult.ok(formDataService.submit(formCode, data, account));
        } catch (IllegalArgumentException e) {
            return ApiResult.fail(e.getMessage());
        }
    }
}
