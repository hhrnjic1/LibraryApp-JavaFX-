package com.example.library;

import javafx.beans.property.SimpleStringProperty;

public class Book {
    private int id;
    private SimpleStringProperty naslov, autor, isbn;

    public SimpleStringProperty naslovProperty() {
        return naslov;
    }

    public SimpleStringProperty autorProperty() {
        return autor;
    }

    public SimpleStringProperty isbnProperty() {
        return isbn;
    }

    public Book() {
        naslov = new SimpleStringProperty();
        autor = new SimpleStringProperty();
        isbn = new SimpleStringProperty();
    }

    public Book(int id, String naslov, String autor, String isbn) {
        this.id = id;
        this.naslov = new SimpleStringProperty(naslov);
        this.autor = new SimpleStringProperty(autor);
        this.isbn = new SimpleStringProperty(isbn);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return  naslov.get();
    }

    public void setNaslov(String naslov) {
        this.naslov.set(naslov);
    }

    public String getAutor() {
        return autor.get();
    }

    public void setAutor(String autor) {
        this.autor.set(autor);
    }

    public String getIsbn() {
        return isbn.get();
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    @Override
    public String toString(){
        return autor.get() + "," + naslov.get();
    }
}
