package org.gszabi15.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.gszabi15.model.Book;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

public class BookRepository {

    private final List<Book> books;

    
    public BookRepository() {
        this.books = new ArrayList<>();
    }

    public String generateUniqueId2() {
        String id = Long.toString(Math.abs(UUID.randomUUID().getMostSignificantBits()), 36);
        if (id.startsWith("-")) id = id.substring(1);
        return id;
    }
    
    private String randomBase62(int length) {
        StringBuilder sb = new StringBuilder(length);
        final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(BASE62.length());
            sb.append(BASE62.charAt(idx));
        }
        return sb.toString();
    }

    public String generateUniqueId() {
        final int LENGTH = 4;

        String id;
        Set<String> existingIds = new HashSet<>();
        for (Book book : books) {
            existingIds.add(book.getId());
        }

        do {
            id = randomBase62(LENGTH);
        } while (existingIds.contains(id));

        return id;
    }

    public Book save(Book book, boolean keepId) {
        if (!keepId || book.getId() == null) {
            book.setId(generateUniqueId());
        }
        books.add(book);
        return book;
    }

    public boolean delete(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book update(String id, Book updatedBook) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    return book;
                }).orElse(null);
    }

    public Book getById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean saveToCSV(String filePath) {
        try (FileWriter out = new FileWriter(filePath);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                     .withHeader("id", "title", "author"))) {
            for (Book book : getAllBooks()) {
                printer.printRecord(book.getId(), book.getTitle(), book.getAuthor());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadFromCSV(String filePath) {
        try (FileReader in = new FileReader(filePath)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("id", "title", "author")
                    .setSkipHeaderRecord(true)
                    .build();

            for (CSVRecord record : csvFormat.parse(in)) {
                Book book = new Book(
                        record.get("id"),
                        record.get("title"),
                        record.get("author")
                );
                save(book, true);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}