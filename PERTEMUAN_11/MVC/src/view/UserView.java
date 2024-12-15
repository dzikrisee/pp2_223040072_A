package PERTEMUAN_11.MVC.src.view;

import PERTEMUAN_11.MVC.src.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class UserView extends JFrame {
    private JTextField txtName = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JButton btnAdd = new JButton("Add User");
    private JButton btnUpdate = new JButton("Update User");
    private JButton btnDelete = new JButton("Delete User");
    private JButton btnRefresh = new JButton("Refresh");
    private JList<String> userList = new JList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private List<User> users;

    public UserView() {
        setTitle("User Management");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(new JLabel("Name: "));
        panel.add(txtName);
        panel.add(new JLabel("Email: "));
        panel.add(txtEmail);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        panel.add(buttonPanel);

        userList.setModel(listModel);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(userList), BorderLayout.CENTER);
    }

    public String getNameINput() {
        return txtName.getText();
    }

    public String getEmailINput() {
        return txtEmail.getText();
    }

    // Mengembalikan ID pengguna yang dipilih
    public int getSelectedUserId() {
        String selectedValue = userList.getSelectedValue();
        if (selectedValue != null) {
            // Misalkan kita menyertakan ID di dalam string
            String[] parts = selectedValue.split(" \\(");
            String name = parts[0]; // Nama pengguna
            // Ambil ID pengguna dari daftar users yang sudah diperbarui
            for (User user : users) {
                if (user.getName().equals(name)) {
                    return user.getId(); // Kembalikan ID pengguna
                }
            }
        }
        return -1; // Jika tidak ada yang dipilih
    }

    // Memperbarui daftar pengguna di JList
    public void setUserList(List<User> users) {
        this.users = users; // Simpan daftar users
        listModel.clear();
        for (User user : users) {
            listModel.addElement(user.getName() + " (" + user.getEmail() + ")");
        }
    }

    public void addAddUserListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void addUpdateUserListener(ActionListener listener) {
        btnUpdate.addActionListener(listener);
    }

    public void addDeleteUserListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public void addRefreshUserListener(ActionListener listener) {
        btnRefresh.addActionListener(listener);
    }
}
