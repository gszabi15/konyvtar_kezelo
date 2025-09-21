/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gszabi15.konyvtar_kezelo.Controller;

import com.gszabi15.konyvtar_kezelo.Model;
import com.gszabi15.konyvtar_kezelo.View;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author INTEC PC
 */
public class SearchByNameOrAuthorCommand implements Controller.Command {
        private final Model model;
        private final List<Model.Book> books;
        
        public SearchByNameOrAuthorCommand(Model model) {
            this.books = new ArrayList<>();
            this.model = model;
        }
        
        @Override
        public void execute() {

            View.InputField input = new View.InputField("==== Könyv keresése cím vagy író alapján ====","Keresés: ");
            input.show();
            String find = input.readInput();

            for (int i = 0; model.getBooks().size() > i; i++) {
                if (model.getBooks().get(i).author.equalsIgnoreCase(find) || model.getBooks().get(i).title.equalsIgnoreCase(find)) {
                    books.add(model.getBooks().get(i));
                }
            }

            if (!books.isEmpty()) {
                View.ListBooks listbooks = new View.ListBooks(books);

                inner: while (true) {
                    View.clearConsole();
                    
                    listbooks.show();
                    switch (listbooks.readInput()) {
                        case "0" -> {
                            break inner;
                        }
                    }
                }
            }
            books.clear();
        }
    }