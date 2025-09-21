/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gszabi15.konyvtar_kezelo.Controller;

import com.gszabi15.konyvtar_kezelo.Model;
import com.gszabi15.konyvtar_kezelo.View;

/**
 *
 * @author INTEC PC
 */
public class LoadOrSaveCommand implements Controller.Command {
    private final Model model;

    public LoadOrSaveCommand(Model model) {
        this.model = model;
    }

    @Override
    public void execute() {
        View.LoadOrSave fileview = new View.LoadOrSave();

        inner: while (true) {
            View.clearConsole();

            fileview.show();

            switch(fileview.readInput()) {
                case "0" -> {
                    break inner;
                }
                case "1" -> {
                    View.InputField input = new View.InputField("================== File Mentése ==================", "File elérési útja: ");
                    if (!model.saveToCSV(input.readInput())) {
                        View.print("Hiba történt a file mentése közben\nTovábblépéshez nyomjon entert!");
                        View.waitChar();
                    }
                    break inner;
                }
                case "2" -> {
                    View.InputField input = new View.InputField("================= File Betöltése =================", "File elérési útja: ");
                    if (!model.loadFromCSV(input.readInput())) {
                        View.print("Hiba történt a file beolvasása közben\nTovábblépéshez nyomjon entert!");
                        View.waitChar();
                    }
                    break inner;
                }
            }

        }

    }
}
