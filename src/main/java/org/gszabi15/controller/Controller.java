/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gszabi15.controller;

import org.gszabi15.service.BookService;
import org.gszabi15.view.MainMenu;
import org.gszabi15.view.UserInputMenu;
import org.gszabi15.view.View;
import java.util.HashMap;
import java.util.Map;

public class Controller {           
    
    private final Map<String, Command> commands = new HashMap<>();
    
    public Controller(BookController bookcontroller, View view) {
        commands.put("1", new AddBookCommand(bookcontroller));
        commands.put("2", new DelBookByIDCommand(bookcontroller));
        commands.put("3", new ModifyBookCommand(bookcontroller));
        commands.put("4", new ListAllBookCommand(bookcontroller));
        commands.put("5", new SearchByNameOrAuthorCommand(bookcontroller));
        commands.put("6", new LoadOrSaveCommand(bookcontroller));
    }

    public void ViewController() {
        
        while (true) {
            View.clearConsole();
            String choice;
            UserInputMenu mainmenu = new MainMenu();
            mainmenu.show();
            choice = mainmenu.readInput();
            
            if ("0".equals(choice)) break;

            Command cmd = commands.get(choice);
            if (cmd != null) {
                cmd.execute();
            }
        }
    }
    
    public interface Command {
        void execute();
    }
}
