/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gszabi15.controller;

import org.gszabi15.model.BookDto;
import org.gszabi15.view.InputField;
import org.gszabi15.view.ListBooks;
import org.gszabi15.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author INTEC PC
 */
public class SearchByNameOrAuthorCommand implements Controller.Command {
        private final BookController bookController;
        private final List<BookDto> books;
        
        public SearchByNameOrAuthorCommand(BookController bookController) {
            this.books = new ArrayList<>();
            this.bookController = bookController;
        }
        
        @Override
        public void execute() {

            InputField input = new InputField("==== Könyv keresése cím vagy író alapján ====","Keresés: ");
            input.show();
            String find = input.readInput();

            bookController.getAllBooks().stream()
                    .filter(b -> b.getTitle().toLowerCase().contains(find.toLowerCase())
                            || b.getAuthor().toLowerCase().contains(find.toLowerCase()))
                    .forEach(books::add);

            if (!books.isEmpty()) {
                ListBooks listbooks = new ListBooks(books);

                while (true) {
                    View.clearConsole();
                    
                    listbooks.show();
                    switch (listbooks.readInput()) {
                        case "0" -> {
                            books.clear();
                            return;
                        }
                    }
                }
            }
        }
    }