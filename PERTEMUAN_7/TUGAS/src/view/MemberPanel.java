package PERTEMUAN_7.TUGAS.src.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

import PERTEMUAN_7.TUGAS.src.dao.MemberDAO;
import PERTEMUAN_7.TUGAS.src.model.Member;

import java.awt.Font;

public class MemberPanel extends JPanel {
    private JTextField nameField, emailField, phoneField;
    private JTextArea addressArea;
    private JComboBox<String> membershipCombo;
    private JList<String> interestsList;
    private JCheckBox activeCheck;
    private JSpinner joinDateSpinner;
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private MemberDAO memberDAO;
    
    public MemberPanel() {
        memberDAO = new MemberDAO();
        setLayout(new BorderLayout());
        
        
        
        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        inputPanel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        inputPanel.add(phoneField, gbc);
        
        // Address
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        JScrollPane addressScroll = new JScrollPane(addressArea);
        inputPanel.add(addressScroll, gbc);
        
        // Membership Type
        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Membership:"), gbc);
        gbc.gridx = 1;
        String[] membershipTypes = {"Regular", "Premium", "VIP"};
        membershipCombo = new JComboBox<>(membershipTypes);
        inputPanel.add(membershipCombo, gbc);
        
        // Interests
        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(new JLabel("Interests:"), gbc);
        gbc.gridx = 1;
        String[] interests = {"Fiction", "Non-Fiction", "Science", "Technology", "Arts", "History"};
        interestsList = new JList<>(interests);
        interestsList.setVisibleRowCount(4);
        JScrollPane interestsScroll = new JScrollPane(interestsList);
        inputPanel.add(interestsScroll, gbc);
        
        // Active Status
        gbc.gridx = 0; gbc.gridy = 6;
        inputPanel.add(new JLabel("Active:"), gbc);
        gbc.gridx = 1;
        activeCheck = new JCheckBox();
        activeCheck.setSelected(true);
        inputPanel.add(activeCheck, gbc);
        
        // Join Date
        gbc.gridx = 0; gbc.gridy = 7;
        inputPanel.add(new JLabel("Join Date:"), gbc);
        gbc.gridx = 1;
        SpinnerDateModel dateModel = new SpinnerDateModel();
        joinDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(joinDateSpinner, "yyyy-MM-dd");
        joinDateSpinner.setEditor(dateEditor);
        inputPanel.add(joinDateSpinner, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        // Table
        String[] columns = {"ID", "Name", "Email", "Phone", "Address", "Membership", "Interests", "Active", "Join Date"};
        tableModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(memberTable);
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        
        // Event Handlers
        addButton.addActionListener(e -> addMember());
        updateButton.addActionListener(e -> updateMember());
        deleteButton.addActionListener(e -> deleteMember());
        clearButton.addActionListener(e -> clearForm());
        
        // Load initial data
        refreshTable();
    }
    
    private void addMember() {
        try {
            Member member = getFormData();
            memberDAO.addMember(member);
            clearForm();
            refreshTable();
            JOptionPane.showMessageDialog(this, "Member added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding member: " + e.getMessage());
        }
    }
    
    private void updateMember() {
        try {
            int selectedRow = memberTable.getSelectedRow();
            if (selectedRow >= 0) {
                Member member = getFormData();
                member.setMemberId((Integer) memberTable.getValueAt(selectedRow, 0));
                memberDAO.updateMember(member);
                clearForm();
                refreshTable();
                JOptionPane.showMessageDialog(this, "Member updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a member to update.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating member: " + e.getMessage());
        }
    }
    
    private void deleteMember() {
        try {
            int selectedRow = memberTable.getSelectedRow();
            if (selectedRow >= 0) {
                int memberId = (Integer) memberTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to delete this member?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
                    
                if (confirm == JOptionPane.YES_OPTION) {
                    memberDAO.deleteMember(memberId);
                    clearForm();
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Member deleted successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a member to delete.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting member: " + e.getMessage());
        }
    }
    
    private Member getFormData() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressArea.getText();
        String membershipType = (String) membershipCombo.getSelectedItem();
        List<String> selectedInterests = interestsList.getSelectedValuesList();
        String interests = String.join(",", selectedInterests);
        boolean activeStatus = activeCheck.isSelected();
        Date joinDate = (Date) joinDateSpinner.getValue();
        
        return new Member(name, email, phone, address, membershipType, 
                         interests, activeStatus, joinDate);
    }
    
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        membershipCombo.setSelectedIndex(0);
        interestsList.clearSelection();
        activeCheck.setSelected(true);
        joinDateSpinner.setValue(new Date());
    }
    
    private void refreshTable() {
        try {
            List<Member> members = memberDAO.getAllMembers();
            tableModel.setRowCount(0);
            for (Member member : members) {
                Object[] row = {
                    member.getMemberId(),
                    member.getName(),
                    member.getEmail(),
                    member.getPhone(),
                    member.getAddress(),
                    member.getMembershipType(),
                    member.getInterests(),
                    member.isActiveStatus(),
                    member.getJoinDate()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading members: " + e.getMessage());
        }
    }
}
