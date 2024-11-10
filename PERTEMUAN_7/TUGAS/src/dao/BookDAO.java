package PERTEMUAN_7.TUGAS.src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import PERTEMUAN_7.TUGAS.src.db.DatabaseConnection;
import PERTEMUAN_7.TUGAS.src.model.Book;
import PERTEMUAN_7.TUGAS.src.view.BookPanel;

public class BookDAO {
    public void addBook(PERTEMUAN_7.TUGAS.src.model.Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, publisher, publish_year, category, stock, location) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setInt(4, book.getPublishYear());
            stmt.setString(5, book.getCategory());
            stmt.setInt(6, book.getStock());
            stmt.setString(7, book.getLocation());
            
            stmt.executeUpdate();
        }
    }
    
    public void updateBook(PERTEMUAN_7.TUGAS.src.model.Book book) throws SQLException {
        String sql = "UPDATE books SET title=?, author=?, publisher=?, publish_year=?, " +
                    "category=?, stock=?, location=? WHERE book_id=?";
                    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setInt(4, book.getPublishYear());
            stmt.setString(5, book.getCategory());
            stmt.setInt(6, book.getStock());
            stmt.setString(7, book.getLocation());
            stmt.setInt(8, book.getBookId());
            
            stmt.executeUpdate();
        }
    }
    
    public void deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }
    
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();// Changed from List<BookDAO>
        String sql = "SELECT * FROM books";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Book book = new Book(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getInt("publish_year"),
                    rs.getString("category"),
                    rs.getInt("stock"),
                    rs.getString("location")
                );
                book.setBookId(rs.getInt("book_id"));
                books.add(book);
            }
        }
        return books;
    }

    public Object getBookId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookId'");
    }

    public Object getTitle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTitle'");
    }

    public Object getAuthor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthor'");
    }

    public Object getPublisher() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPublisher'");
    }

    public Object getCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategory'");
    }

    public Object getLocation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLocation'");
    }

    public Object getStock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStock'");
    }
}