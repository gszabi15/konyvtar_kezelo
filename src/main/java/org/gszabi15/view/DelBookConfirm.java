package org.gszabi15.view;

import org.gszabi15.model.BookDto;

import static org.gszabi15.view.View.prefix;
import static org.gszabi15.view.View.scanner;

public class DelBookConfirm implements UserInputMenu {
    private final BookDto book;

    public DelBookConfirm(BookDto book) {  // args-ban jön az ID
        this.book = book;
    }

    @Override
    public void show() {
        System.out.println("================= Biztosan törli? =================");
        System.out.println("Könyv ID: " + book.getId());
        System.out.println("Könyv címe: " + book.getTitle());
        System.out.println("Könyv írója: " + book.getAuthor());
        System.out.println();
        System.out.println("1. Törlés");
        System.out.println("0. Vissza");
    }
    @Override
    public String readInput() {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}