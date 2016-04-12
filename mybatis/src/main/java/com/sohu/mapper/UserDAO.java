package com.sohu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sohu.model.User;

public interface UserDAO {
    @Select("SELECT * FROM t_user WHERE id = #{id}")
    User getUser(@Param("id") Integer id);

    @Select("select * from t_user")
    public List<User> getAllUsers();

    @Select("select *from t_user where name like #{name}")
    public User retrieveUserByIdAndName(@Param("name") String name);

    @Insert("INSERT INTO t_user(name,mobile) "
            + "VALUES(#{userName},#{mobile})")
    public void addNewUser(User user);

    @Delete("delete from user where id=#{id}")
    public void deleteUser(int id);

    @Update("update user set name=#{name},mobile=#{mobile}" + " where id=#{id}")
    public void updateUser(User user);
}
