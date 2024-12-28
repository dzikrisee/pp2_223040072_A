package view;

import model.User;

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
    private List<User> users = new ArrayList<>();
    private JButton btnExportPdf = new JButton("Export to PDF");
    private List<Integer> userIds;
    private JProgressBar progressBar;
    private JLabel progressLabel;
    private JPanel progressPanel;

    public UserView() {
        setTitle("User Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Name field
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(new JLabel("Name: "), BorderLayout.WEST);
        namePanel.add(txtName, BorderLayout.CENTER);
        inputPanel.add(namePanel);

        // Email field
        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.add(new JLabel("Email: "), BorderLayout.WEST);
        emailPanel.add(txtEmail, BorderLayout.CENTER);
        inputPanel.add(emailPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnExportPdf);
        inputPanel.add(buttonPanel);

        // List Panel
        userList.setModel(listModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Progress Panel
        progressPanel = new JPanel(new BorderLayout(5, 5));
        progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        progressLabel = new JLabel("Status Progress");
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(progressBar.getPreferredSize().width, 25));

        progressPanel.add(progressLabel, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);

        // Add all components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(progressPanel, BorderLayout.SOUTH);

        // Set initial progress
        progressBar.setValue(0);
        progressLabel.setText("Ready");

        // Display the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getNameINput() {
        return txtName.getText();
    }

    public String getEmailINput() {
        return txtEmail.getText();
    }

    public int getSelectedUserId() {
        if (users == null || users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No user data available. Please refresh the list.");
            return -1;
        }

        String selectedValue = userList.getSelectedValue();
        if (selectedValue != null) {
            String[] parts = selectedValue.split(" \\(");
            String name = parts[0];
            for (User user : users) {
                if (user.getName().equals(name)) {
                    return user.getId();
                }
            }
        }

        JOptionPane.showMessageDialog(this, "No user selected.");
        return -1;
    }

    public void setUserList(List<User> users) {
        listModel.clear();
        this.users = users;
        userIds = new ArrayList<>();
        for (User user : users) {
            listModel.addElement(user.getName() + " (" + user.getEmail() + ")");
            userIds.add(user.getId());
        }
    }

    public void setProgressBarVisible(boolean visible) {
        progressPanel.setVisible(visible);
        progressPanel.revalidate();
        progressPanel.repaint();
    }

    public void setProgressBarValue(int value) {
        progressBar.setValue(value);
        progressBar.repaint();
    }

    public void setProgressBarMessage(String message) {
        progressLabel.setText(message);
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

    public void addExportPdfListener(ActionListener listener) {
        btnExportPdf.addActionListener(listener);
    }
}