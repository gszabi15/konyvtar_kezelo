/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gszabi15.konyvtar_kezelo.Controller;

import com.gszabi15.konyvtar_kezelo.Model;
import com.gszabi15.konyvtar_kezelo.View;


public class ListAllBookCommand implements Controller.Command {
        private final Model model;

        public ListAllBookCommand(Model model) {
            this.model = model;
        }

        @Override
        public void execute() {
            inner: while (true) {
                View.clearConsole();
                
                View.ListBooks listallbook = new View.ListBooks(model.getBooks());
                listallbook.show();
                switch (listallbook.readInput()) {
                    case "0" -> {
                        break inner;
                    }
                }
            }
        }
    }
