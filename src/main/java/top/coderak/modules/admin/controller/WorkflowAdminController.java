package top.coderak.modules.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.coderak.core.api.ApiResult;
import top.coderak.core.utils.JWTUtils;
import top.coderak.entity.WorkflowDefinition;
import top.coderak.entity.WorkflowInstance;
import top.coderak.entity.WorkflowTask;
import top.coderak.service.WorkflowDefinitionService;
import top.coderak.service.WorkflowEngineService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "工作流管理")
@RestController
@RequestMapping("/admin/workflow")
public class WorkflowAdminController {

    @Autowired
    private WorkflowDefinitionService workflowDefinitionService;

    @Autowired
    private WorkflowEngineService workflowEngineService;

    private String getAccount(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return JWTUtils.getAccount(authHeader.substring(7));
        }
        return null;
    }

    @ApiOperation("工作流定义列表")
    @GetMapping("/definition/list")
    public ApiResult<List<WorkflowDefinition>> listDefinitions() {
        return ApiResult.ok(workflowDefinitionService.listAll());
    }

    @ApiOperation("已发布工作流")
    @GetMapping("/definition/published")
    public ApiResult<List<WorkflowDefinition>> listPublished() {
        return ApiResult.ok(workflowDefinitionService.listPublished());
    }

    @ApiOperation("工作流定义详情")
    @GetMapping("/definition/{id}")
    public ApiResult<WorkflowDefinition> getDefinition(@PathVariable String id) {
        return ApiResult.ok(workflowDefinitionService.getById(id));
    }

    @ApiOperation("新增工作流定义")
    @PostMapping("/definition/add")
    public ApiResult<Void> addDefinition(@RequestBody WorkflowDefinition def, HttpServletRequest request) {
        String account = getAccount(request);
        return workflowDefinitionService.add(def, account) ? ApiResult.ok() : ApiResult.fail("Add failed");
    }

    @ApiOperation("更新工作流定义")
    @PostMapping("/definition/update")
    public ApiResult<Void> updateDefinition(@RequestBody WorkflowDefinition def, HttpServletRequest request) {
        String account = getAccount(request);
        return workflowDefinitionService.update(def, account) ? ApiResult.ok() : ApiResult.fail("Update failed");
    }

    @ApiOperation("删除工作流定义")
    @PostMapping("/definition/delete")
    public ApiResult<Void> deleteDefinition(@RequestParam String id, HttpServletRequest request) {
        String account = getAccount(request);
        return workflowDefinitionService.delete(id, account) ? ApiResult.ok() : ApiResult.fail("Delete failed");
    }

    @ApiOperation("发布工作流定义")
    @PostMapping("/definition/publish")
    public ApiResult<Void> publishDefinition(@RequestParam String id, HttpServletRequest request) {
        String account = getAccount(request);
        return workflowDefinitionService.publish(id, account) ? ApiResult.ok() : ApiResult.fail("Publish failed");
    }

    @ApiOperation("流程实例列表")
    @GetMapping("/instance/list")
    public ApiResult<List<WorkflowInstance>> listInstances() {
        return ApiResult.ok(workflowEngineService.listInstances());
    }

    @ApiOperation("我发起的流程")
    @GetMapping("/instance/mine")
    public ApiResult<List<WorkflowInstance>> myInstances(HttpServletRequest request) {
        String account = getAccount(request);
        return ApiResult.ok(workflowEngineService.listMyInstances(account));
    }

    @ApiOperation("发起流程")
    @PostMapping("/instance/start")
    public ApiResult<WorkflowInstance> startInstance(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String account = getAccount(request);
        if (account == null) {
            return ApiResult.fail("Not logged in");
        }
        try {
            WorkflowInstance instance = workflowEngineService.start(
                    body.get("workflowCode"),
                    body.get("formDataId"),
                    body.get("businessKey"),
                    account);
            return ApiResult.ok(instance);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    @ApiOperation("待办任务")
    @GetMapping("/task/todo")
    public ApiResult<List<WorkflowTask>> todoTasks(HttpServletRequest request) {
        String account = getAccount(request);
        return ApiResult.ok(workflowEngineService.listTodo(account));
    }

    @ApiOperation("实例任务历史")
    @GetMapping("/task/list")
    public ApiResult<List<WorkflowTask>> tasksByInstance(@RequestParam String instanceId) {
        return ApiResult.ok(workflowEngineService.listTasksByInstance(instanceId));
    }

    @ApiOperation("审批通过")
    @PostMapping("/task/approve")
    public ApiResult<Void> approve(
            @RequestParam String taskId,
            @RequestParam(required = false) String comment,
            HttpServletRequest request) {
        String account = getAccount(request);
        return workflowEngineService.approve(taskId, comment, account) ? ApiResult.ok() : ApiResult.fail("Approve failed");
    }

    @ApiOperation("审批驳回")
    @PostMapping("/task/reject")
    public ApiResult<Void> reject(
            @RequestParam String taskId,
            @RequestParam(required = false) String comment,
            HttpServletRequest request) {
        String account = getAccount(request);
        return workflowEngineService.reject(taskId, comment, account) ? ApiResult.ok() : ApiResult.fail("Reject failed");
    }
}
