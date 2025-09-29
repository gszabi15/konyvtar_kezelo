/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gszabi15.controller;

import org.gszabi15.model.BookDto;
import org.gszabi15.view.DelBookConfirm;
import org.gszabi15.view.InputField;
import org.gszabi15.view.View;

import java.util.Optional;

/**
 *
 * @author INTEC PC
 */
public class DelBookByIDCommand implements Controller.Command {
        private final BookController bookController;
        
        public DelBookByIDCommand(BookController bookController) {
            this.bookController = bookController;
        }

        @Override
        public void execute() {
            View.clearConsole();

            InputField input = new InputField("============= Könyv törlése ID alapján =============", "ID: ");
            input.show();

            String id = input.readInput();
            BookDto book = bookController.getBookById(id);

            if (book != null) {
                while (true) {
                    View.clearConsole();

                    DelBookConfirm confirm = new DelBookConfirm(book);
                    confirm.show();

                    switch(confirm.readInput()){
                        case "0" -> {
                            return;
                        }
                        case "1" -> {
                            if (!bookController.deleteBook(id)) {
                                View.print("Nem sikerült törölni a könyvet!\nTovábblépéshez nyomjon entert!");
                                View.waitChar();
                            }
                            return;
                        }
                    }
                }
            } else {
                View.print("Nem sikerült a könyvet megtalálni ID alapján!\nTovábblépéshez nyomjon entert!");
                View.waitChar();
            }
        }
    }
