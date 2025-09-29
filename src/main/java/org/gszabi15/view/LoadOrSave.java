package org.gszabi15.view;

import static org.gszabi15.view.View.prefix;
import static org.gszabi15.view.View.scanner;

public  class LoadOrSave implements UserInputMenu {
    @Override
    public void show() {
        System.out.println("==  Könyvek mentése vagy betöltése csv file-ból ==");
        System.out.println("1. Mentés");
        System.out.println("2. Betöltés");
        System.out.println("0. Vissza");
    }
    @Override
    public String readInput() {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}