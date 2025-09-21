/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gszabi15.konyvtar_kezelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author INTEC PC
 */
public class Model {
    
    
    // -------------------------------------- Book modell -------------------------------------------
    private final List<Book> books = new ArrayList<>();
    private int nextId = 0;
    
    public boolean deleteBook(Book book) {
        if (book != null && books.contains(book)) {
            books.remove(book);
            return true;
        }
        return false;
    }
    
    public boolean setBook(int id, Book newBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).id == id) {
                books.set(i, newBook);
                return true;
            }
        }
        return false;
    }
    
    public Book createBook(String title, String author) {
        Book book = new Book(nextId++, title, author);
        books.add(book);
        return book;
    }

    public List<Book> getBooks() {
        return books;
    }
    
    public class Book {
        public final int id;
        public final String title;
        public final String author;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }
        
        @Override
        public String toString() {
            return id + " - " + title + " (" + author + ")";
        }
        
        /*public String toCSV() {
            return id + "," + title + "," + author;
        }*/
    }
    
    
    // ---------------------------------------  CSV kezelés ---------------------------------------
    
    public boolean saveToCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {

            writer.write('\ufeff'); // BOM Excel-hez
            writer.write("id;title;author"); // fejléc
            writer.newLine();

            for (Book book : books) {
                writer.write(book.id + ";\"" + book.title + "\";\"" + book.author + "\"");
                writer.newLine();
            }

        return true;

        } catch (IOException e) {
            return false;
        }
    }

    
    public boolean loadFromCSV(String filePath) {
        books.clear(); // töröljük a meglévő listát
        nextId = 0;
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // BOM kezelése
            reader.mark(1);
            if (reader.read() != 0xFEFF) {
                reader.reset();
            }

            String line = reader.readLine();
            if (line == null) {
                return false; // kilépünk a metódusból
            }
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 3);
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();

                    if (title.startsWith("\"") && title.endsWith("\"")) {
                        title = title.substring(1, title.length() - 1);
                    }
                    if (author.startsWith("\"") && author.endsWith("\"")) {
                        author = author.substring(1, author.length() - 1);
                    }

                    Book book = new Book(id, title, author);
                    books.add(book);

                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }


            return true;

        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }
    
}
