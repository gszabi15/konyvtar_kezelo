package org.gszabi15.repository;

import org.gszabi15.model.Book;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class BookRepository {

    private final List<Book> books;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    
    public BookRepository() {
        this.books = new ArrayList<>();
    }

    private String randomBase62(int len) {
        StringBuilder sb = new StringBuilder(len);
        final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final SecureRandom random = new SecureRandom();

        for (int i = 0; i < len; i++) {
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

    public void save(Book book) {
        if (book.getId() == null || books.stream().anyMatch(b -> b.getId().equals(book.getId()))) {
            book.setId(generateUniqueId());
        }
        books.add(book);
    }

    public boolean delete(String id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void update(String id, Book updatedBook) {
        books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    public Optional<Book> getById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public boolean saveToCSV(String filePath) {
        try (FileWriter out = new FileWriter(filePath);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
            printer.printRecord(ID, TITLE, AUTHOR);

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
                    .setHeader(ID, TITLE, AUTHOR)
                    .setSkipHeaderRecord(true)
                    .build();

            for (CSVRecord rec : csvFormat.parse(in)) {
                Book book = new Book(
                        rec.get(ID),
                        rec.get(TITLE),
                        rec.get(AUTHOR)
                );
                save(book);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}