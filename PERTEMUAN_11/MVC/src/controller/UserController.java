package PERTEMUAN_11.MVC.src.controller;

import PERTEMUAN_11.MVC.src.model.MyBatisUtil;
import PERTEMUAN_11.MVC.src.model.User;
import PERTEMUAN_11.MVC.src.model.UserMapper;
import PERTEMUAN_11.MVC.src.view.UserView;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserController {
    private UserView view;
    private UserMapper mapper;

    public UserController(UserView view, UserMapper mapper) {
        this.view = view;
        this.mapper = mapper;

        // Menambahkan listener untuk tombol Add, Update, Delete, dan Refresh
        this.view.addAddUserListener(new AddUserListener());
        this.view.addUpdateUserListener(new UpdateUserListener());
        this.view.addDeleteUserListener(new DeleteUserListener());
        this.view.addRefreshUserListener(new RefreshListener());
    }

    class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameINput();
            String email = view.getEmailINput();

            if (!name.isEmpty() && !email.isEmpty()) {
                User user = new User();
                user.setName(name);
                user.setEmail(email);

                SqlSession session = MyBatisUtil.getSqlSession();
                try {
                    UserMapper mapper = session.getMapper(UserMapper.class);
                    mapper.insert(user);
                    session.commit();
                    JOptionPane.showMessageDialog(view, "User added successfully!");
                } catch (Exception ex) {
                    session.rollback();
                    JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
                } finally {
                    session.close();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
            }
        }
    }

    class UpdateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameINput();
            String email = view.getEmailINput();
            int userId = view.getSelectedUserId(); // Ambil ID pengguna yang dipilih

            if (!name.isEmpty() && !email.isEmpty() && userId != -1) {
                User user = new User();
                user.setId(userId); // Set ID yang ingin diperbarui
                user.setName(name);
                user.setEmail(email);

                SqlSession session = MyBatisUtil.getSqlSession();
                try {
                    UserMapper mapper = session.getMapper(UserMapper.class);
                    mapper.update(user);
                    session.commit();
                    JOptionPane.showMessageDialog(view, "User updated successfully!");
                } catch (Exception ex) {
                    session.rollback();
                    JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
                } finally {
                    session.close();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Please fill in all fields and select a user to update.");
            }
        }
    }

    class DeleteUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int userId = view.getSelectedUserId(); // Ambil ID pengguna yang dipilih untuk dihapus

            if (userId != -1) {
                SqlSession session = MyBatisUtil.getSqlSession();
                try {
                    UserMapper mapper = session.getMapper(UserMapper.class);
                    mapper.delete(userId);
                    session.commit();
                    JOptionPane.showMessageDialog(view, "User deleted successfully!");
                } catch (Exception ex) {
                    session.rollback();
                    JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
                } finally {
                    session.close();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Please select a user to delete.");
            }
        }
    }

    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SqlSession session = MyBatisUtil.getSqlSession();
            try {
                UserMapper mapper = session.getMapper(UserMapper.class);
                List<User> users = mapper.getAll();
                view.setUserList(users); // Memperbarui list pengguna
            } finally {
                session.close();
            }
        }
    }
}
