package top.coderak.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.coderak.entity.sys.*;
import top.coderak.mapper.SysExtMapper;
import top.coderak.service.SysExtService;
import top.coderak.service.support.FlagCrudHelper;

import java.util.List;
import java.util.Map;

@Service
public class SysExtServiceImpl implements SysExtService {

    @Autowired
    private SysExtMapper sysExtMapper;

    private boolean isNew(String id) {
        return id == null || id.isEmpty();
    }

    @Override
    public List<SysDept> listDepts() {
        return sysExtMapper.selectAllDepts();
    }

    @Override
    public boolean saveDept(SysDept dept, String operator) {
        if (isNew(dept.getId())) {
            dept.setId(FlagCrudHelper.newId());
            FlagCrudHelper.onCreate(dept, operator);
            return sysExtMapper.insertDept(dept) > 0;
        }
        FlagCrudHelper.onUpdate(dept, operator);
        return sysExtMapper.updateDept(dept) > 0;
    }

    @Override
    public boolean deleteDept(String id, String operator) {
        SysDept dept = sysExtMapper.selectDeptById(id);
        if (dept == null) return false;
        FlagCrudHelper.onDelete(dept, operator);
        return sysExtMapper.updateDept(dept) > 0;
    }

    @Override
    public List<SysDictType> listDictTypes() {
        return sysExtMapper.selectAllDictTypes();
    }

    @Override
    public boolean saveDictType(SysDictType row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            if (row.getStatus() == null) row.setStatus("enabled");
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertDictType(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateDictType(row) > 0;
    }

    @Override
    public boolean deleteDictType(String id, String operator) {
        SysDictType row = listDictTypes().stream().filter(d -> id.equals(d.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateDictType(row) > 0;
    }

    @Override
    public List<SysDictData> listDictData(String dictCode) {
        if (dictCode == null || dictCode.isEmpty()) {
            return sysExtMapper.selectAllDictData();
        }
        return sysExtMapper.selectDictDataByCode(dictCode);
    }

    @Override
    public boolean saveDictData(SysDictData row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            if (row.getStatus() == null) row.setStatus("enabled");
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertDictData(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateDictData(row) > 0;
    }

    @Override
    public boolean deleteDictData(String id, String operator) {
        SysDictData row = sysExtMapper.selectAllDictData().stream().filter(d -> id.equals(d.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateDictData(row) > 0;
    }

    @Override
    public List<SysOperLog> listOperLogs() {
        return sysExtMapper.selectAllOperLogs();
    }

    @Override
    public boolean deleteOperLog(String id) {
        return sysExtMapper.deleteOperLog(id) > 0;
    }

    @Override
    public List<SysLoginLog> listLoginLogs() {
        return sysExtMapper.selectAllLoginLogs();
    }

    @Override
    public boolean deleteLoginLog(String id) {
        return sysExtMapper.deleteLoginLog(id) > 0;
    }

    @Override
    public List<SysConfig> listConfigs() {
        return sysExtMapper.selectAllConfigs();
    }

    @Override
    public boolean saveConfig(SysConfig row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertConfig(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateConfig(row) > 0;
    }

    @Override
    public boolean deleteConfig(String id, String operator) {
        SysConfig row = listConfigs().stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateConfig(row) > 0;
    }

    @Override
    public List<SysNotice> listNotices() {
        return sysExtMapper.selectAllNotices();
    }

    @Override
    public boolean saveNotice(SysNotice row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            if (row.getStatus() == null) row.setStatus("draft");
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertNotice(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateNotice(row) > 0;
    }

    @Override
    public boolean deleteNotice(String id, String operator) {
        SysNotice row = listNotices().stream().filter(n -> id.equals(n.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateNotice(row) > 0;
    }

    @Override
    public List<SysFile> listFiles() {
        return sysExtMapper.selectAllFiles();
    }

    @Override
    public boolean saveFile(SysFile row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertFile(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateFile(row) > 0;
    }

    @Override
    public boolean deleteFile(String id, String operator) {
        SysFile row = listFiles().stream().filter(f -> id.equals(f.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateFile(row) > 0;
    }

    @Override
    public List<SysJob> listJobs() {
        return sysExtMapper.selectAllJobs();
    }

    @Override
    public boolean saveJob(SysJob row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            if (row.getStatus() == null) row.setStatus("paused");
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertJob(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateJob(row) > 0;
    }

    @Override
    public boolean deleteJob(String id, String operator) {
        SysJob row = listJobs().stream().filter(j -> id.equals(j.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateJob(row) > 0;
    }

    @Override
    public List<BizOrder> listOrders() {
        return sysExtMapper.selectAllOrders();
    }

    @Override
    public boolean saveOrder(BizOrder row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertOrder(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateOrder(row) > 0;
    }

    @Override
    public boolean deleteOrder(String id, String operator) {
        BizOrder row = listOrders().stream().filter(o -> id.equals(o.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateOrder(row) > 0;
    }

    @Override
    public List<BizProduct> listProducts() {
        return sysExtMapper.selectAllProducts();
    }

    @Override
    public boolean saveProduct(BizProduct row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertProduct(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateProduct(row) > 0;
    }

    @Override
    public boolean deleteProduct(String id, String operator) {
        BizProduct row = listProducts().stream().filter(p -> id.equals(p.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateProduct(row) > 0;
    }

    @Override
    public List<BizCustomer> listCustomers() {
        return sysExtMapper.selectAllCustomers();
    }

    @Override
    public boolean saveCustomer(BizCustomer row, String operator) {
        if (isNew(row.getId())) {
            row.setId(FlagCrudHelper.newId());
            FlagCrudHelper.onCreate(row, operator);
            return sysExtMapper.insertCustomer(row) > 0;
        }
        FlagCrudHelper.onUpdate(row, operator);
        return sysExtMapper.updateCustomer(row) > 0;
    }

    @Override
    public boolean deleteCustomer(String id, String operator) {
        BizCustomer row = listCustomers().stream().filter(c -> id.equals(c.getId())).findFirst().orElse(null);
        if (row == null) return false;
        FlagCrudHelper.onDelete(row, operator);
        return sysExtMapper.updateCustomer(row) > 0;
    }

    @Override
    public Map<String, Object> dashboardCounts() {
        return sysExtMapper.selectDashboardCounts();
    }
}
