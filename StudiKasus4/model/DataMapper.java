package StudiKasus4.model;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DataMapper {

    @Insert("INSERT INTO inventory (name, category, price, quantity) VALUES (#{name}, #{category}, #{price}, #{quantity})")
    void insertInventory(Inventory inventory);

    @Select("SELECT * FROM inventory")
    List<Inventory> getAllInventories();

    @Select("SELECT * FROM inventory WHERE id = #{id}")
    Inventory getInventoryById(int id);

    @Update("UPDATE inventory SET name = #{name}, category = #{category}, price = #{price}, quantity = #{quantity} WHERE id = #{id}")
    void updateInventory(Inventory inventory);

    @Delete("DELETE FROM inventory WHERE id = #{id}")
    void deleteInventory(int id);
}
