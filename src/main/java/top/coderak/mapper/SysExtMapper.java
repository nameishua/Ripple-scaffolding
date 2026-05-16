package top.coderak.mapper;

import org.apache.ibatis.annotations.Param;
import top.coderak.entity.sys.*;

import java.util.List;
import java.util.Map;

public interface SysExtMapper {

    List<SysDept> selectAllDepts();
    SysDept selectDeptById(@Param("id") String id);
    int insertDept(SysDept row);
    int updateDept(SysDept row);

    List<SysDictType> selectAllDictTypes();
    int insertDictType(SysDictType row);
    int updateDictType(SysDictType row);

    List<SysDictData> selectAllDictData();
    List<SysDictData> selectDictDataByCode(@Param("dictCode") String dictCode);
    int insertDictData(SysDictData row);
    int updateDictData(SysDictData row);

    List<SysOperLog> selectAllOperLogs();
    int insertOperLog(SysOperLog row);
    int deleteOperLog(@Param("id") String id);

    List<SysLoginLog> selectAllLoginLogs();
    int insertLoginLog(SysLoginLog row);
    int deleteLoginLog(@Param("id") String id);

    List<SysConfig> selectAllConfigs();
    int insertConfig(SysConfig row);
    int updateConfig(SysConfig row);

    List<SysNotice> selectAllNotices();
    int insertNotice(SysNotice row);
    int updateNotice(SysNotice row);

    List<SysFile> selectAllFiles();
    int insertFile(SysFile row);
    int updateFile(SysFile row);

    List<SysJob> selectAllJobs();
    int insertJob(SysJob row);
    int updateJob(SysJob row);

    List<BizOrder> selectAllOrders();
    int insertOrder(BizOrder row);
    int updateOrder(BizOrder row);

    List<BizProduct> selectAllProducts();
    int insertProduct(BizProduct row);
    int updateProduct(BizProduct row);

    List<BizCustomer> selectAllCustomers();
    int insertCustomer(BizCustomer row);
    int updateCustomer(BizCustomer row);

    Map<String, Object> selectDashboardCounts();
}
