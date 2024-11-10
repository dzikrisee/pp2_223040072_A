package PERTEMUAN_7.TUGAS.src.model;

import java.util.List;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private int publishYear;
    private String category;
    private int stock;
    private String location;
    private List<Book> Book;

    // Constructor
    public Book(String title, String author, String publisher, int publishYear,
               String category, int stock, String location) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.category = category;
        this.stock = stock;
        this.location = location;
    }

    // Getters and Setters
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}