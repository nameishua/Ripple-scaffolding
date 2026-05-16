package top.coderak.modules.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.coderak.core.api.ApiResult;
import top.coderak.core.utils.JWTUtils;
import top.coderak.entity.Permission;
import top.coderak.entity.sys.*;
import top.coderak.service.PermissionService;
import top.coderak.service.SysExtService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "系统扩展模块")
@RestController
public class SysExtAdminController {

    @Autowired
    private SysExtService sysExtService;

    @Autowired
    private PermissionService permissionService;

    private String account(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String a = JWTUtils.getAccount(auth.substring(7));
            if (a != null) return a;
        }
        return "admin";
    }

    @ApiOperation("仪表盘统计")
    @GetMapping("/admin/dashboard/stats")
    public ApiResult<Map<String, Object>> dashboardStats() {
        return ApiResult.ok(sysExtService.dashboardCounts());
    }

    @GetMapping("/admin/dept/list")
    public ApiResult<List<SysDept>> deptList() {
        return ApiResult.ok(sysExtService.listDepts());
    }

    @PostMapping("/admin/dept/save")
    public ApiResult<Void> deptSave(@RequestBody SysDept dept, HttpServletRequest request) {
        return sysExtService.saveDept(dept, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/dept/delete")
    public ApiResult<Void> deptDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteDept(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/dict/type/list")
    public ApiResult<List<SysDictType>> dictTypeList() {
        return ApiResult.ok(sysExtService.listDictTypes());
    }

    @PostMapping("/admin/dict/type/save")
    public ApiResult<Void> dictTypeSave(@RequestBody SysDictType row, HttpServletRequest request) {
        return sysExtService.saveDictType(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/dict/type/delete")
    public ApiResult<Void> dictTypeDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteDictType(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/dict/data/list")
    public ApiResult<List<SysDictData>> dictDataList(@RequestParam(required = false) String dictCode) {
        return ApiResult.ok(sysExtService.listDictData(dictCode));
    }

    @PostMapping("/admin/dict/data/save")
    public ApiResult<Void> dictDataSave(@RequestBody SysDictData row, HttpServletRequest request) {
        return sysExtService.saveDictData(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/dict/data/delete")
    public ApiResult<Void> dictDataDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteDictData(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/log/oper/list")
    public ApiResult<List<SysOperLog>> operLogList() {
        return ApiResult.ok(sysExtService.listOperLogs());
    }

    @PostMapping("/admin/log/oper/delete")
    public ApiResult<Void> operLogDelete(@RequestParam String id) {
        return sysExtService.deleteOperLog(id) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/log/login/list")
    public ApiResult<List<SysLoginLog>> loginLogList() {
        return ApiResult.ok(sysExtService.listLoginLogs());
    }

    @PostMapping("/admin/log/login/delete")
    public ApiResult<Void> loginLogDelete(@RequestParam String id) {
        return sysExtService.deleteLoginLog(id) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/config/list")
    public ApiResult<List<SysConfig>> configList() {
        return ApiResult.ok(sysExtService.listConfigs());
    }

    @PostMapping("/admin/config/save")
    public ApiResult<Void> configSave(@RequestBody SysConfig row, HttpServletRequest request) {
        return sysExtService.saveConfig(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/config/delete")
    public ApiResult<Void> configDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteConfig(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/notice/list")
    public ApiResult<List<SysNotice>> noticeList() {
        return ApiResult.ok(sysExtService.listNotices());
    }

    @PostMapping("/admin/notice/save")
    public ApiResult<Void> noticeSave(@RequestBody SysNotice row, HttpServletRequest request) {
        return sysExtService.saveNotice(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/notice/delete")
    public ApiResult<Void> noticeDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteNotice(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/file/list")
    public ApiResult<List<SysFile>> fileList() {
        return ApiResult.ok(sysExtService.listFiles());
    }

    @PostMapping("/admin/file/save")
    public ApiResult<Void> fileSave(@RequestBody SysFile row, HttpServletRequest request) {
        return sysExtService.saveFile(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/file/delete")
    public ApiResult<Void> fileDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteFile(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/job/list")
    public ApiResult<List<SysJob>> jobList() {
        return ApiResult.ok(sysExtService.listJobs());
    }

    @PostMapping("/admin/job/save")
    public ApiResult<Void> jobSave(@RequestBody SysJob row, HttpServletRequest request) {
        return sysExtService.saveJob(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/job/delete")
    public ApiResult<Void> jobDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteJob(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/biz/order/list")
    public ApiResult<List<BizOrder>> orderList() {
        return ApiResult.ok(sysExtService.listOrders());
    }

    @PostMapping("/admin/biz/order/save")
    public ApiResult<Void> orderSave(@RequestBody BizOrder row, HttpServletRequest request) {
        return sysExtService.saveOrder(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/biz/order/delete")
    public ApiResult<Void> orderDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteOrder(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/biz/product/list")
    public ApiResult<List<BizProduct>> productList() {
        return ApiResult.ok(sysExtService.listProducts());
    }

    @PostMapping("/admin/biz/product/save")
    public ApiResult<Void> productSave(@RequestBody BizProduct row, HttpServletRequest request) {
        return sysExtService.saveProduct(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/biz/product/delete")
    public ApiResult<Void> productDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteProduct(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/biz/customer/list")
    public ApiResult<List<BizCustomer>> customerList() {
        return ApiResult.ok(sysExtService.listCustomers());
    }

    @PostMapping("/admin/biz/customer/save")
    public ApiResult<Void> customerSave(@RequestBody BizCustomer row, HttpServletRequest request) {
        return sysExtService.saveCustomer(row, account(request)) ? ApiResult.ok() : ApiResult.fail("save failed");
    }

    @PostMapping("/admin/biz/customer/delete")
    public ApiResult<Void> customerDelete(@RequestParam String id, HttpServletRequest request) {
        return sysExtService.deleteCustomer(id, account(request)) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }

    @GetMapping("/admin/permission/list")
    public ApiResult<List<Permission>> permissionList() {
        return ApiResult.ok(permissionService.getAllPermissions());
    }

    @PostMapping("/admin/permission/add")
    public ApiResult<Void> permissionAdd(@RequestBody Permission permission) {
        return permissionService.addPermission(permission) ? ApiResult.ok() : ApiResult.fail("add failed");
    }

    @PostMapping("/admin/permission/update")
    public ApiResult<Void> permissionUpdate(@RequestBody Permission permission) {
        return permissionService.updatePermission(permission) ? ApiResult.ok() : ApiResult.fail("update failed");
    }

    @PostMapping("/admin/permission/delete")
    public ApiResult<Void> permissionDelete(@RequestParam String id) {
        return permissionService.deletePermission(id) ? ApiResult.ok() : ApiResult.fail("delete failed");
    }
}
