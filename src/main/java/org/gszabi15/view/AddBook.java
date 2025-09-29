package org.gszabi15.view;

import static org.gszabi15.view.View.prefix;
import static org.gszabi15.view.View.scanner;

public class AddBook implements UserInputMenu {
    public String id;
    public String title;
    public String author;

    public AddBook(String id) {  // args-ban jön az ID
        this.id = id;
    }


    @Override
    public void show() {
        System.out.println("=============== Új könyv hozzáadása ===============");
        System.out.println("Könyv ID: " + id);
        System.out.println("1. Könyv címe: " + title);
        System.out.println("2. Könyv írója: " + author);
        System.out.println("3. Mentés");
        System.out.println("0. Vissza");
    }
    @Override
    public String readInput() {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}