package model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM users")
    List<model.User> getAll();

    @Insert("INSERT INTO users (name, email) VALUES (#{name}, #{email})")
    void insert(model.User user);

    @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
    void update(model.User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(int id);
}
