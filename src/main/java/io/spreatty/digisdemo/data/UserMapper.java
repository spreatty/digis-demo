package io.spreatty.digisdemo.data;

import io.spreatty.digisdemo.pojo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM `User` WHERE `id` = #{id}")
    @Results({
            @Result(column = "full_name", property = "fullName"),
            @Result(column = "birth_date", property = "birthDate")
    })
    User getUser(int id);

    @Select("SELECT * FROM `User`")
    @Results({
            @Result(column = "full_name", property = "fullName"),
            @Result(column = "birth_date", property = "birthDate")
    })
    List<User> getAllUsers();

    @Select("SELECT * FROM `User` WHERE `login` = #{login}")
    @Results({
            @Result(column = "full_name", property = "fullName"),
            @Result(column = "birth_date", property = "birthDate")
    })
    User getUserByLogin(String login);

    @SelectKey(statement = "SELECT LAST_INSERT_ID() as `id`", keyProperty = "id", resultType = Integer.class, before = false)
    @Insert("INSERT INTO `User` (`login`, `full_name`, `gender`, `birth_date`) VALUES (#{login}, #{fullName}, #{gender}, #{birthDate})")
    int saveUser(User user);

    @Update("UPDATE `User` SET `login` = #{login}, `full_name` = #{fullName}, `gender` = #{gender}, `birth_date` = #{birthDate} where `id` = #{id}")
    int updateUser(User person);
}
