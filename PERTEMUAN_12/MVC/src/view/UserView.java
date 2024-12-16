package PERTEMUAN_12.MVC.src.view;

import PERTEMUAN_12.MVC.src.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private List<User> users = new ArrayList<>(); // Inisialisasi langsung untuk menghindari null
    private JButton btnExportPdf = new JButton("Export to PDF");
    private List<Integer> userIds;

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
        buttonPanel.add(btnExportPdf);
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
        if (users == null || users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No user data available. Please refresh the list.");
            return -1;
        }

        String selectedValue = userList.getSelectedValue();
        if (selectedValue != null) {
            String[] parts = selectedValue.split(" \\("); // Memisahkan Nama (Email)
            String name = parts[0]; // Ambil nama dari string
            for (User user : users) {
                if (user.getName().equals(name)) {
                    return user.getId(); // Mengembalikan ID pengguna
                }
            }
        }

        JOptionPane.showMessageDialog(this, "No user selected.");
        return -1; // Tidak ada user yang dipilih
    }

    // Memperbarui daftar pengguna di JList
    public void setUserList(List<User> users) {
        listModel.clear();
        this.users = users; // Update atribut users dengan data baru
        userIds = new ArrayList<>(); // Reset ID list setiap kali data diperbarui
        for (User user : users) {
            listModel.addElement(user.getName() + " (" + user.getEmail() + ")");
            userIds.add(user.getId()); // Simpan ID pengguna
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

    public void addExportPdfListener(ActionListener actionListener) {
        btnExportPdf.addActionListener(actionListener);
    }
}
