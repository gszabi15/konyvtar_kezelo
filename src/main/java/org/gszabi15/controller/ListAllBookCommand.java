/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gszabi15.controller;

import org.gszabi15.view.ListBooks;
import org.gszabi15.view.View;


public class ListAllBookCommand implements Controller.Command {
        private final BookController bookController;

        public ListAllBookCommand(BookController bookController) {
            this.bookController = bookController;
        }

        @Override
        public void execute() {
            while (true) {
                View.clearConsole();
                
                ListBooks booksList = new ListBooks(bookController.getAllBooks());
                booksList.show();
                if (booksList.readInput().equals("0")) {
                    return;
                }
            }
        }
    }
