package org.gszabi15.view;

import static org.gszabi15.view.View.prefix;
import static org.gszabi15.view.View.scanner;

public class EditBook implements UserInputMenu {
    public String id;
    public String title;
    public String author;

    public EditBook() {  // args-ban jön az ID

    }

    @Override
    public void show() {
        System.out.println("================ Könyv módosítása ================");
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