package org.gszabi15;

import org.gszabi15.controller.BookController;
import org.gszabi15.model.BookDto;
import org.gszabi15.repository.BookRepository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext("org.gszabi15");

        BookController bookController = context.getBean(BookController.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);

        while (true) {
            int choice = mainMenu();

            switch (choice) {
                case 0 -> { return; }
                case 1 -> addBook(bookController, context.getBean(BookDto.class));
                case 2 -> delBookById(bookController);
                case 3 -> modifyBook(bookController);
                case 4 -> listAllBooks(bookController);
                case 5 -> searchByNameOrAuthor(bookController);
                case 6 -> loadOrSave(bookRepository);
            }
        }

    }

    public static void waitChar() {
        java.io.Console console = System.console();
        if (console != null) {
            console.readPassword("");
        } else {
            scanner.nextLine();
        }
    }

    public static int mainMenu() {
        while (true) {
            System.out.println("===================== Főmenü =====================");
            System.out.println("1. Új könyv hozzáadása");
            System.out.println("2. Könyv törlés ID alapján");
            System.out.println("3. Könyv módosítása");
            System.out.println("4. Összes könyv kilistázása");
            System.out.println("5. Könyv keresése cím vagy író alapján");
            System.out.println("6. Könyvek mentése vagy betöltése csv file-ból");
            System.out.println("0. Kilépés");

            System.out.print("> ");
            String choiceStr = scanner.nextLine();

            try {
                int choice = Integer.parseInt(choiceStr);

                if (choice >= 0 && choice <= 6) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {/**/}
        }
    }

    public static boolean bookConfirm(String title, BookDto book, String action) {
        while (true) {
            System.out.println(title);
            System.out.println("Könyv ID: " + book.getId());
            System.out.println("Könyv címe: " + book.getTitle());
            System.out.println("Könyv írója: " + book.getAuthor());
            System.out.println();
            System.out.println("1. " + action);
            System.out.println("0. Vissza");

            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equals("1") || input.equals("0")) {
                return input.equals("1");
            }

        }
    }

    public static int bookShow(String title, BookDto book) {
        while (true) {
            System.out.println(title);
            System.out.println("Könyv ID: " + book.getId());
            System.out.println("1. Könyv címe: " + book.getTitle());
            System.out.println("2. Könyv írója: " + book.getAuthor());
            System.out.println("3. Mentés");
            System.out.println("0. Vissza");

            System.out.print("> ");
            String choiceStr = scanner.nextLine();

            try {
                int choice = Integer.parseInt(choiceStr);

                if (choice >= 0 && choice <= 3) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {/**/}
        }
    }

    private static String askInput(String title, String prompt) {
        System.out.println(title);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void addBook(BookController bookController, BookDto book) {
        String id = bookController.generateUniqueId();
        String title = "=============== Új könyv hozzáadása ===============";

        book.setId(id);

        while (true) {
            int choice = bookShow(title, book);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> book.setTitle(askInput(title, "Könyv címe: "));
                case 2 -> book.setAuthor(askInput(title, "Könyv írója: "));
                case 3 -> {
                    if (bookConfirm("================= Biztosan menti? =================", book, "Mentés")) {
                        bookController.createBook(book);
                        return;
                    }
                }
            }
        }
    }

    public static void delBookById(BookController bookController) {

        String id = askInput("============= Könyv törlése ID alapján =============", "ID: ");

        BookDto book = bookController.getBookById(id);

        if (book != null) {

            if (bookConfirm("================= Biztosan törli? =================", book, "Törlés")) {
                if (!bookController.deleteBook(id)) {
                    System.out.print("Nem sikerült törölni a könyvet!\nTovábblépéshez nyomjon entert!");
                    waitChar();
                }
            }

        } else {
            System.out.print("Nem sikerült a könyvet megtalálni ID alapján!\nTovábblépéshez nyomjon entert!");
            waitChar();
        }
    }

    public static void modifyBook(BookController bookController) {
        String find = askInput("== Könyv keresése cím, író vagy ID alapján ==", "Keresés: ");
        String title = "================ Könyv módosítása ================";

        BookDto book = bookController.getAllBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(find.toLowerCase())
                        || b.getAuthor().toLowerCase().contains(find.toLowerCase())
                        || b.getId().equals(find))
                .findFirst()
                .orElse(null);

        if (book == null) return;

        while (true) {
            int choice = bookShow(title, book);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> book.setTitle(askInput(title, "Könyv címe: "));
                case 2 -> book.setAuthor(askInput(title, "Könyv írója: "));
                case 3 -> {
                    if (bookConfirm("================= Biztosan menti? =================", book, "Mentés")) {
                        bookController.updateBook(book.getId(), book);
                        return;
                    }
                }
            }
        }
    }

    public static void listBooks(String title, List<BookDto> books) {
        while (true) {
            System.out.println(title);
            for (BookDto book : books) {
                System.out.println(book.toString());
            }
            System.out.println();
            System.out.println("0. Vissza");

            System.out.print("> ");
            if (scanner.nextLine().equals("0")) {
                return;
            }
        }
    }

    public static void listAllBooks(BookController bookController) {
        listBooks("============ Összes könyv kilistázása ============", bookController.getAllBooks());
    }

    public static void loadOrSave(BookRepository bookRepository) {
        while (true) {

            System.out.println("==  Könyvek mentése vagy betöltése csv file-ból ==");
            System.out.println("1. Mentés");
            System.out.println("2. Betöltés");
            System.out.println("0. Vissza");

            System.out.print("> ");
            switch(scanner.nextLine()) {
                case "0" -> {
                    return;
                }
                case "1" -> {
                    String input = askInput("================== File Mentése ==================", "File elérési útja: ");
                    if (!bookRepository.saveToCSV(input)) {
                        System.out.print("Hiba történt a file mentése közben\nTovábblépéshez nyomjon entert!");
                        waitChar();
                    }
                    return;
                }
                case "2" -> {
                    String input = askInput("================= File Betöltése =================", "File elérési útja: ");
                    if (!bookRepository.loadFromCSV(input)) {
                        System.out.print("Hiba történt a file beolvasása közben\nTovábblépéshez nyomjon entert!");
                        waitChar();
                    }
                    return;
                }
            }
        }
    }

    public static void searchByNameOrAuthor(BookController bookController) {

        String find = askInput("==== Könyv keresése cím vagy író alapján ====","Keresés: ");

        List<BookDto> books = new ArrayList<>();

        bookController.getAllBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(find.toLowerCase())
                        || b.getAuthor().toLowerCase().contains(find.toLowerCase()))
                .forEach(books::add);

        if (!books.isEmpty()) {
            listBooks("========== Keresett könyvek kilistázása ==========", books);
        }
    }
}