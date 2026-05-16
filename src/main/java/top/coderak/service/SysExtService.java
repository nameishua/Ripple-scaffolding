package top.coderak.service;

import top.coderak.entity.sys.*;

import java.util.List;
import java.util.Map;

public interface SysExtService {

    List<SysDept> listDepts();
    boolean saveDept(SysDept dept, String operator);
    boolean deleteDept(String id, String operator);

    List<SysDictType> listDictTypes();
    boolean saveDictType(SysDictType row, String operator);
    boolean deleteDictType(String id, String operator);

    List<SysDictData> listDictData(String dictCode);
    boolean saveDictData(SysDictData row, String operator);
    boolean deleteDictData(String id, String operator);

    List<SysOperLog> listOperLogs();
    boolean deleteOperLog(String id);

    List<SysLoginLog> listLoginLogs();
    boolean deleteLoginLog(String id);

    List<SysConfig> listConfigs();
    boolean saveConfig(SysConfig row, String operator);
    boolean deleteConfig(String id, String operator);

    List<SysNotice> listNotices();
    boolean saveNotice(SysNotice row, String operator);
    boolean deleteNotice(String id, String operator);

    List<SysFile> listFiles();
    boolean saveFile(SysFile row, String operator);
    boolean deleteFile(String id, String operator);

    List<SysJob> listJobs();
    boolean saveJob(SysJob row, String operator);
    boolean deleteJob(String id, String operator);

    List<BizOrder> listOrders();
    boolean saveOrder(BizOrder row, String operator);
    boolean deleteOrder(String id, String operator);

    List<BizProduct> listProducts();
    boolean saveProduct(BizProduct row, String operator);
    boolean deleteProduct(String id, String operator);

    List<BizCustomer> listCustomers();
    boolean saveCustomer(BizCustomer row, String operator);
    boolean deleteCustomer(String id, String operator);

    Map<String, Object> dashboardCounts();
}
