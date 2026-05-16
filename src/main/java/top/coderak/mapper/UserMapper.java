package top.coderak.mapper;

import org.apache.ibatis.annotations.*;
import top.coderak.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user(id,name,age) values (#{id},#{name},#{age})")
    int addUser(User user);

    @Update("update user set name=#{name},age=#{age} where id=#{id}")
    int updateUser(User user);

    @Delete("delete from user where id=#{id}")
    int deleteUser(String id);

    @Select("SELECT * FROM user where name=#{userName}")
    User findByName(@Param("userName") String userName);

    @Select("SELECT * FROM user WHERE flag != '删除'")
    List<User> findAllAnnotation();

    List<User> findAllXml();

    @Select("SELECT * FROM user WHERE account = #{account}")
    User selectByAccount(@Param("account") String account);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(@Param("id") String id);

    @Insert("INSERT INTO user (id, name, age, account, password, flag, sort, code, create_by, create_date, update_by, update_date) " +
            "VALUES (#{id}, #{name}, #{age}, #{account}, #{password}, #{flag}, #{sort}, #{code}, #{createBy}, #{createDate}, #{updateBy}, #{updateDate})")
    int insert(User user);

    @Update("UPDATE user SET name=#{name}, age=#{age}, account=#{account}, password=#{password}, flag=#{flag}, " +
            "sort=#{sort}, code=#{code}, update_by=#{updateBy}, update_date=#{updateDate} WHERE id=#{id}")
    int updateById(User user);
}
