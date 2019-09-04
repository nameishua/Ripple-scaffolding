package top.coderak.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import top.coderak.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 功能描述
 *
 * @author zyh
 * @date 2019/7/21 0021
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 用户数据新增
     */
    @Insert("insert into user(id,name,age) values (#{id},#{name},#{age})")
    void addUser(User user);

    /**
     * 用户数据修改
     */
    @Update("update user set name=#{name},age=#{age} where id=#{id}")
    void updateUser(User user);

    /**
     * 用户数据删除
     */
    @Delete("delete from user where id=#{id}")
    void deleteUser(String id);

    /**
     * 根据用户名称查询用户信息
     */
    @Select("SELECT * FROM user where name=#{userName}")
    User findByName(@Param("userName") String userName);

    /**
     * 查询所有(注解)
     */
    @Select("SELECT * FROM user")
    List<User> findAllAnnotation();

    /**
     * 查询所有（xml）
     */
    List<User> findAllXml();

    User selectByAccount(String account);


}
