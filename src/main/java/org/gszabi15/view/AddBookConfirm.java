package org.gszabi15.view;

import static org.gszabi15.view.View.prefix;
import static org.gszabi15.view.View.scanner;

public class AddBookConfirm implements UserInputMenu {
    String id;
    String title;
    String author;

    public AddBookConfirm(String id, String title, String author) {  // args-ban jön az ID
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public void show() {
        System.out.println("================= Biztosan menti? =================");
        System.out.println("Könyv ID: " + id);
        System.out.println("Könyv címe: " + title);
        System.out.println("Könyv írója: " + author);
        System.out.println();
        System.out.println("1. Mentés");
        System.out.println("0. Vissza");
    }

    @Override
    public String readInput() {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}