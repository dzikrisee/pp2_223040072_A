package StudiKasus4.controller;

import StudiKasus4.model.*;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class InventoryController {

    public void addInventory(Inventory inventory) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {  // Perbaikan di sini
            DataMapper mapper = session.getMapper(DataMapper.class);
            mapper.insertInventory(inventory);
            session.commit();
        }
    }

    public List<Inventory> getAllInventories() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {  // Perbaikan di sini
            DataMapper mapper = session.getMapper(DataMapper.class);
            return mapper.getAllInventories();
        }
    }

    public void updateInventory(Inventory inventory) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {  // Perbaikan di sini
            DataMapper mapper = session.getMapper(DataMapper.class);
            mapper.updateInventory(inventory);
            session.commit();
        }
    }

    public void deleteInventory(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {  // Perbaikan di sini
            DataMapper mapper = session.getMapper(DataMapper.class);
            mapper.deleteInventory(id);
            session.commit();
        }
    }

    public Inventory getInventoryById(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {  // Perbaikan di sini
            DataMapper mapper = session.getMapper(DataMapper.class);
            return mapper.getInventoryById(id);
        }
    }
}
