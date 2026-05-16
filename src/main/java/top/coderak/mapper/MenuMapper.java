package top.coderak.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.coderak.entity.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<Menu> selectMenusByRoleIds(@Param("roleIds") List<String> roleIds);

    List<Menu> selectAllMenus();

    List<Menu> selectMenuTree();

    int insert(Menu menu);

    int updateById(Menu menu);

    Menu selectById(@Param("id") String id);
}
