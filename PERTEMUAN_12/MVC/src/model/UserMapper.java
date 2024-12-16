package PERTEMUAN_12.MVC.src.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM users")
    List<PERTEMUAN_12.MVC.src.model.User> getAll();

    @Insert("INSERT INTO users (name, email) VALUES (#{name}, #{email})")
    void insert(PERTEMUAN_12.MVC.src.model.User user);

    @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
    void update(PERTEMUAN_12.MVC.src.model.User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(int id);
}
