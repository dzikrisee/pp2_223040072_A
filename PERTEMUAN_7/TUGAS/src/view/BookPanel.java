package PERTEMUAN_7.TUGAS.src.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import PERTEMUAN_7.TUGAS.src.dao.BookDAO;
import PERTEMUAN_7.TUGAS.src.model.Book;

public class BookPanel extends JPanel {
    private JTextField titleField, authorField, publisherField;
    private JSpinner yearSpinner, stockSpinner;
    private JComboBox<String> categoryCombo;
    private JTextField locationField;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private BookDAO bookDAO;
    
    
    public BookPanel() {
        
        ArrayList<Book> books = new ArrayList<>();
        bookDAO = new BookDAO();
        setLayout(new BorderLayout());
        
        
        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(30);
        inputPanel.add(titleField, gbc);
        
        // Author
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        authorField = new JTextField(30);
        inputPanel.add(authorField, gbc);
        
        // Publisher
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Publisher:"), gbc);
        gbc.gridx = 1;
        publisherField = new JTextField(30);
        inputPanel.add(publisherField, gbc);
        
        // Publish Year
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Publish Year:"), gbc);
        gbc.gridx = 1;
        SpinnerNumberModel yearModel = new SpinnerNumberModel(2024, 1900, 2024, 1);
        yearSpinner = new JSpinner(yearModel);
        inputPanel.add(yearSpinner, gbc);
        
        // Category
        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        String[] categories = {"Fiction", "Non-Fiction", "Science", "Technology", "Arts", "History"};
        categoryCombo = new JComboBox<>(categories);
        inputPanel.add(categoryCombo, gbc);
        
        // Stock
        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        SpinnerNumberModel stockModel = new SpinnerNumberModel(0, 0, 1000, 1);
        stockSpinner = new JSpinner(stockModel);
        inputPanel.add(stockSpinner, gbc);
        
        // Location
        gbc.gridx = 0; gbc.gridy = 6;
        inputPanel.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        locationField = new JTextField(30);
        inputPanel.add(locationField, gbc);
        
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
        String[] columns = {"ID", "Title", "Author", "Publisher", "Year", "Category", "Stock", "Location"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(bookTable);
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        
        // Event Handlers
        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        clearButton.addActionListener(e -> clearForm());
        
        // Load initial data
        refreshTable();

        try {
            bookDAO = new BookDAO();
            getComponents();  // Pindahkan semua inisialisasi komponen ke method terpisah
            refreshTable();         // Panggil setelah semua komponen diinisialisasi
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error initializing panel: " + e.getMessage(), 
                "Initialization Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addBook() {
        try {
            Book book = getFormData();
            bookDAO.addBook(book);
            clearForm();
            refreshTable();
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
        }
    }
    


    private void updateBook() {
        try {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book book = getFormData();
                book.setBookId((Integer) bookTable.getValueAt(selectedRow, 0));
                bookDAO.updateBook(book);
                clearForm();
                refreshTable();
                JOptionPane.showMessageDialog(this, "Book updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to update.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating book: " + e.getMessage());
        }
    }


            private void deleteBook() {
                try {
                    int selectedRow = bookTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int bookId = (Integer) bookTable.getValueAt(selectedRow, 0);
                        int confirm = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to delete this book?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION);
                            
                        if (confirm == JOptionPane.YES_OPTION) {
                            bookDAO.deleteBook(bookId);
                            clearForm();
                            refreshTable();
                            JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select a book to delete.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error deleting book: " + e.getMessage());
                }
            }
            
            private Book getFormData() {
                String title = titleField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();
                int publishYear = (Integer) yearSpinner.getValue();
                String category = (String) categoryCombo.getSelectedItem();
                int stock = (Integer) stockSpinner.getValue();
                String location = locationField.getText();
                
                return new Book(title, author, publisher, publishYear, 
                               category, stock, location);
            }
            
            private void clearForm() {
                titleField.setText("");
                authorField.setText("");
                publisherField.setText("");
                yearSpinner.setValue(2024);
                categoryCombo.setSelectedIndex(0);
                stockSpinner.setValue(0);
                locationField.setText("");
            }
            
            private void refreshTable() {
                try {
                    System.out.println("Fetching books from database...");
                    List<Book> books = bookDAO.getAllBooks();
                    System.out.println("Found " + books.size() + " books");
                    
                    tableModel.setRowCount(0);
                    for (Book book : books) {
                        Object[] row = {
                            book.getBookId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getPublisher(),
                            book.getPublishYear(),
                            book.getCategory(),
                            book.getStock(),
                            book.getLocation()
                        };
                        tableModel.addRow(row);
                    }
                } catch (Exception e) {
                    e.printStackTrace();  // Tambahkan ini untuk melihat stack trace lengkap
                    JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
                }
            }
        }