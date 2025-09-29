package org.gszabi15.view;

import org.gszabi15.controller.BookController;
import org.gszabi15.model.BookDto;
import org.gszabi15.service.BookService;

import java.util.List;

import static org.gszabi15.view.View.prefix;
import static org.gszabi15.view.View.scanner;

public class ListBooks implements UserInputMenu {
    private final List<BookDto> books;

    public ListBooks(List<BookDto> books) {
        this.books = books;  // args-ban jön az ID
    }

    @Override
    public void show() {
        System.out.println("============ Összes könyv kilistázása ============");
        for (BookDto book : books) {
            System.out.println(book.toString());
        }
        System.out.println();
        System.out.println("0. Vissza");
    }

    @Override
    public String readInput() {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}