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
public class AddBookCommand implements Controller.Command {
    private final Model model;

    public AddBookCommand(Model model) {
        this.model = model;
    }

    @Override
    public void execute() {
        int id = 0;
        if (!model.getBooks().isEmpty()) {
            id = model.getBooks().get( model.getBooks().size() -1 ).id + 1;
        }
        View.AddBook newbook = new View.AddBook(id);

        inner: while (true) {
            View.clearConsole();
            newbook.show();

            View.clearConsole();
            switch (View.getInput()){
                case "0" -> {
                    break inner;
                }
                case "1" -> {
                    View.InputField input = new View.InputField("=============== Új könyv hozzáadása ===============", "Könyv címe: ");
                    input.show();
                    newbook.title = input.readInput();
                }
                case "2" -> {
                    View.InputField input = new View.InputField("=============== Új könyv hozzáadása ===============", "Könyv írója: ");
                    input.show();
                    newbook.author = input.readInput();

                }
                case "3" -> {

                    inner2: while (true) {                        
                        View.AddBookConfirm confirm = new View.AddBookConfirm(newbook.id, newbook.title, newbook.author);
                        confirm.show();

                        switch (confirm.readInput()) {
                            case "1" -> {
                                model.createBook(newbook.title, newbook.author);
                                break inner;
                            }
                            case "0" -> {
                                break inner2;
                            }
                        }
                    }
                }
            }
        }
    }
}
