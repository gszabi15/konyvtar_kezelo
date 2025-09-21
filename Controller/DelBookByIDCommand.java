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
public class DelBookByIDCommand implements Controller.Command {
        private final Model model;
        
        public DelBookByIDCommand(Model model) {
            this.model = model;
        }
        
        @Override
        public void execute() {
            inner: while (true) { 
                View.clearConsole();
                
                View.InputField input = new View.InputField("============= Könyv törlése ID alapján =============", "ID: ");
                input.show();
                int id = -1;
                try {
                    id = Integer.parseInt(input.readInput());
                } catch (NumberFormatException e) {
                    View.print("Érvénytelen ID-t adtál meg!");
                }
                Model.Book book = null;
                
                if (id != -1) {
                    if (id < model.getBooks().size()) {
                        if (model.getBooks().get(id).id == id) {
                            book = model.getBooks().get(id);
                        } else {
                            for (int i = 0; model.getBooks().size() > i; i++) {
                                if (model.getBooks().get(i).id == id) {
                                    book = model.getBooks().get(i);
                                }
                            }
                        }
                    }
                }
                if (book != null) {
                    inner2: while (true) {
                        View.clearConsole();
                        
                        View.DelBookConfirm confirm = new View.DelBookConfirm(book);
                        confirm.show();
                        
                        switch(confirm.readInput()){
                            case "0" -> {
                                break inner;
                            }
                            case "1" -> {
                                if (!model.deleteBook(book)) {
                                    View.print("Nem sikerült törölni a könyvet!\nTovábblépéshez nyomjon entert!");
                                    View.waitChar();
                                }
                                break inner;
                            }
                        }
                    }
                } else {
                    View.print("Nem sikerült a könyvet megtalálni ID alapján!\nTovábblépéshez nyomjon entert!");
                    View.waitChar();
                    break;
                }
            }
        }
    }
