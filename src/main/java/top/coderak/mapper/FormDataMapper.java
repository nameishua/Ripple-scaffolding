package top.coderak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.coderak.entity.FormData;

import java.util.List;

@Mapper
public interface FormDataMapper {

    List<FormData> selectAll();

    List<FormData> selectByFormCode(@Param("formCode") String formCode);

    FormData selectById(@Param("id") String id);

    int insert(FormData entity);

    int updateById(FormData entity);
}
