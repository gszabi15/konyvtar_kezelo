/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gszabi15.konyvtar_kezelo.Controller;

import com.gszabi15.konyvtar_kezelo.Model;
import com.gszabi15.konyvtar_kezelo.View;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author INTEC PC
 */
public class Controller {           
    
    private final Map<String, Command> commands = new HashMap<>();
    
    public Controller(Model model, View view) {
        commands.put("1", new AddBookCommand(model));
        commands.put("2", new DelBookByIDCommand(model));
        commands.put("3", new ModifyBookCommand(model));
        commands.put("4", new ListAllBookCommand(model));
        commands.put("5", new SearchByNameOrAuthorCommand(model));
        commands.put("6", new LoadOrSaveCommand(model));
    }

    public void ViewController() {
        
        while (true) {
            View.clearConsole();
            String choice;
            View.MainMenu mainmenu = new View.MainMenu();
            mainmenu.show();
            choice = mainmenu.readInput();
            
            if ("0".equals(choice)) break;

            Command cmd = commands.get(choice);
            if (cmd != null) {
                cmd.execute();
            } else {
                View.print("❌ Érvénytelen választás!\n");
            }

        }
    }
    
    public interface Command {
        void execute();
    }
}
