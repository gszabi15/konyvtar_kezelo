package org.gszabi15.view;

import static org.gszabi15.view.View.scanner;

public class InputField implements UserInputMenu {
    private final String title;
    private final String prefix;

    public InputField(String title, String prefix) {
        this.title = title;
        this.prefix = prefix;
    }

    @Override
    public void show() {
        System.out.println(title);
    }

    @Override
    public String readInput() {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}