/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gszabi15.controller;

import org.gszabi15.model.BookDto;
import org.gszabi15.view.AddBook;
import org.gszabi15.view.AddBookConfirm;
import org.gszabi15.view.InputField;
import org.gszabi15.view.View;


public class AddBookCommand implements Controller.Command {
    private final BookController bookController;

    public AddBookCommand(BookController bookController) {
        this.bookController = bookController;
    }

    @Override
    public void execute() {

        String id = bookController.generateUniqueId();

        BookDto book = new BookDto(id, "", "");

        AddBook newbook = new AddBook(id);

        while (true) {
            View.clearConsole();
            newbook.show();
            switch (View.getInput()) {
                case "0" -> { return; } // kilépés
                case "1" -> newbook.title = askInput("Könyv címe: ");
                case "2" -> newbook.author = askInput("Könyv írója: ");
                case "3" -> {
                    book.setTitle(newbook.title);
                    book.setAuthor(newbook.author);
                    if (confirmBook(book)) {
                        bookController.createBook(book, true);
                        return;
                    }
                }
            }
        }
    }
    private String askInput(String prompt) {
        InputField input = new InputField("=============== Új könyv hozzáadása ===============", prompt);
        input.show();
        return input.readInput();
    }

    private boolean confirmBook(BookDto book) {
        while (true) {
            AddBookConfirm confirm = new AddBookConfirm(book.getId(), book.getTitle(), book.getAuthor());
            confirm.show();
            switch (confirm.readInput()) {
                case "1": return true;
                case "0": return false;
            }
        }
    }
}

