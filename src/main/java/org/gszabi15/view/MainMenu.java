package org.gszabi15.view;

import static org.gszabi15.view.View.prefix;
import static org.gszabi15.view.View.scanner;

public class MainMenu implements UserInputMenu {
    @Override
    public void show() {
        System.out.println("===================== FŐMENÜ =====================");
        System.out.println("1. Új könyv hozzáadása");
        System.out.println("2. Könyv törlés ID alapján");
        System.out.println("3. Könyv módosítása");
        System.out.println("4. Összes könyv kilistázása");
        System.out.println("5. Könyv keresése cím vagy író alapján");
        System.out.println("6. Könyvek mentése vagy betöltése csv file-ból");
        System.out.println("0. Kilépés");
    }
    @Override
    public String readInput() {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}