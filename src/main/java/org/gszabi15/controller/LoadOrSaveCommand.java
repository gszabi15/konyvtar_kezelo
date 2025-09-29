/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gszabi15.controller;

import org.gszabi15.view.InputField;
import org.gszabi15.view.LoadOrSave;
import org.gszabi15.view.UserInputMenu;
import org.gszabi15.view.View;

public class LoadOrSaveCommand implements Controller.Command {
    private final BookController bookController;

    public LoadOrSaveCommand(BookController bookController) {
        this.bookController = bookController;
    }

    @Override
    public void execute() {
        UserInputMenu loadOrSaveMenu = new LoadOrSave();

        while (true) {
            View.clearConsole();

            loadOrSaveMenu.show();

            switch(loadOrSaveMenu.readInput()) {
                case "0" -> {
                    return;
                }
                case "1" -> {
                    InputField input = new InputField("================== File Mentése ==================", "File elérési útja: ");
                    if (!bookController.saveToCSV(input.readInput())) {
                        View.print("Hiba történt a file mentése közben\nTovábblépéshez nyomjon entert!");
                        View.waitChar();
                    }
                    return;
                }
                case "2" -> {
                    InputField input = new InputField("================= File Betöltése =================", "File elérési útja: ");
                    if (!bookController.loadFromCSV(input.readInput())) {
                        View.print("Hiba történt a file beolvasása közben\nTovábblépéshez nyomjon entert!");
                        View.waitChar();
                    }
                    return;
                }
            }

        }

    }
}
