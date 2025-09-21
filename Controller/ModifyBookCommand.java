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
public class ModifyBookCommand implements Controller.Command {
    private final Model model;

    public ModifyBookCommand(Model model) {
        this.model = model;
    }

    @Override
    public void execute() {
        View.InputField input = new View.InputField("== Könyv keresése cím, író vagy ID alapján ==","Keresés: ");
            input.show();
            String find = input.readInput();
            Model.Book book = null;

            int id = -1;
            try {
                id = Integer.parseInt(find);
            } catch (NumberFormatException e) {
            }

            if (id == -1 || model.getBooks().size() < id) {
                for (int i = 0; model.getBooks().size() > i; i++) {
                    if (model.getBooks().get(i).author.equalsIgnoreCase(find) || model.getBooks().get(i).title.equalsIgnoreCase(find)) {
                        book = model.getBooks().get(i);
                    }
                }
            } else {
                book = model.getBooks().get(id);
            }

            if (book != null) {
                View.EditBook edit = new View.EditBook();
                edit.id = book.id;
                edit.title = book.title;
                edit.author = book.author;

                inner: while (true) {
                View.clearConsole();

                edit.show();

                View.clearConsole();
                switch (View.getInput()){
                    case "0" -> {
                        break inner;
                    }
                    case "1" -> {
                        View.InputField input2 = new View.InputField("================ Könyv módosítása ================", "Könyv címe: ");
                        input2.show();
                        edit.title = input2.readInput();
                    }
                    case "2" -> {
                        View.InputField input2 = new View.InputField("================ Könyv módosítása ================", "Könyv írója: ");
                        input2.show();
                        edit.author = input2.readInput();
                    }
                    case "3" -> {

                        inner2: while (true) {
                            View.AddBookConfirm confirm = new View.AddBookConfirm(edit.id, edit.title, edit.author);
                            confirm.show();

                            switch (confirm.readInput()) {
                                case "1" -> {
                                    Model.Book newBook = model.new Book(book.id, edit.title, edit.author);

                                    model.setBook(book.id, newBook);
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
}