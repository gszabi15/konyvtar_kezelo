/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gszabi15.konyvtar_kezelo;

import com.gszabi15.konyvtar_kezelo.Model.Book;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author INTEC PC
 */

// Singleton
public class View {
    static {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        } catch (Exception e) {
            
        }
    }

    
    static Scanner scanner = new Scanner(System.in);
    public static String prefix = "> ";
       

    public View() {
    
    }

    // ------------------------------------------  Interfacek --------------------------------------------
    
    public interface Menu {
        void show();
    }
    
    public interface UserInputMenu  {
        void show();
        String readInput();
    }
    
    // -----------------------------------------  Menu rendszer -------------------------------------------
    public static class MainMenu implements UserInputMenu {
        @Override
        public void show() {
            System.out.println("===================== FŐMENÜ =====================");
            System.out.println("1. Új könyv hozzáadása");
            System.out.println("2. Könyv törlés ID alapján");
            System.out.println("3. Könyv módosítása");
            System.out.println("4. Összes könyv kilistázása");
            System.out.println("5. Könyv keresése cím vagy író alapján");
            System.out.println("6. Könyvek mentése vagy betöltése csv file-ból");
            System.out.println("0. Kilépés");
        }
        @Override
        public String readInput() {
            System.out.print(prefix);
            return scanner.nextLine();
        }
    }
    
    public static class AddBook implements Menu {       
        public int id;
        public String title;
        public String author;
        
        public AddBook(int id) {  // args-ban jön az ID
            this.id = id;
        }
        
        @Override
        public void show() {
            System.out.println("=============== Új könyv hozzáadása ===============");
            System.out.println("Könyv ID: " + id);
            System.out.println("1. Könyv címe: " + title);
            System.out.println("2. Könyv írója: " + author);
            System.out.println("3. Mentés");
            System.out.println("0. Vissza");
        }
    }
    public static class AddBookConfirm implements UserInputMenu {       
        int id;
        String title;
        String author;
        
        public AddBookConfirm(int id, String title, String author) {  // args-ban jön az ID
            this.id = id;
            this.title = title;
            this.author = author;
        }
        
        @Override
        public void show() {
            System.out.println("================= Biztosan menti? =================");
            System.out.println("Könyv ID: " + id);
            System.out.println("Könyv címe: " + title);
            System.out.println("Könyv írója: " + author);
            System.out.println();
            System.out.println("1. Mentés");
            System.out.println("0. Vissza");
        }
        
        @Override
        public String readInput() {
            System.out.print(prefix);
            return scanner.nextLine();
        }
    }
        
    public static class DelBookConfirm implements UserInputMenu { 
        private final Book book;

        public DelBookConfirm(Book book) {  // args-ban jön az ID
            this.book = book;
        }
        
        @Override
        public void show() {
            System.out.println("================= Biztosan törli? =================");
            System.out.println("Könyv ID: " + book.id);
            System.out.println("Könyv címe: " + book.title);
            System.out.println("Könyv írója: " + book.author);
            System.out.println();
            System.out.println("1. Törlés");
            System.out.println("0. Vissza");
        }
        @Override
        public String readInput() {
            System.out.print(prefix);
            return scanner.nextLine();
        }
    }
    
    public static class EditBook implements Menu { 
        public int id;
        public String title;
        public String author;

        public EditBook() {  // args-ban jön az ID
            
        }
        
        @Override
        public void show() {
            System.out.println("================ Könyv módosítása ================");
            System.out.println("Könyv ID: " + id);
            System.out.println("1. Könyv címe: " + title);
            System.out.println("2. Könyv írója: " + author);
            System.out.println("3. Mentés");
            System.out.println("0. Vissza");
        }
    }
    
    public static class ListBooks implements UserInputMenu { 
        private final List<Book> books;

        public ListBooks(List<Book> books) {  // args-ban jön az ID
            this.books = books;
        }
        
        @Override
        public void show() {
            System.out.println("============ Összes könyv kilistázása ============");
            for (int i = 0; i < books.size(); i++) {
                System.out.println(books.get(i).toString());
            }
            System.out.println();
            System.out.println("0. Vissza");
        }
        
        @Override
        public String readInput() {
            System.out.print(prefix);
            return scanner.nextLine();
        }
    }
    
    public static class LoadOrSave implements UserInputMenu {        
        @Override
        public void show() {
            System.out.println("==  Könyvek mentése vagy betöltése csv file-ból ==");
            System.out.println("1. Mentés");
            System.out.println("2. Betöltés");
            System.out.println("0. Vissza");
        }
        
        @Override
        public String readInput() {
            System.out.print(prefix);
            return scanner.nextLine();
        }
    }
    
    public static class InputField implements UserInputMenu {
        private final String title;
        private final String prefix;
        
        public InputField(String title, String prefix) {
            this.title = title;
            this.prefix = prefix;
        }
        
        @Override
        public void show() {
            System.out.println(title);
        }
        
        @Override
        public String readInput() {
            System.out.print(prefix);
            return scanner.nextLine();
        }
    }
    
    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final IOException e)
        {
            //System.out.println(e);
        }
    }
    
    public static String getInput(String pref) {
        if (pref.isEmpty()){
            pref = prefix;
        }
        System.out.print(pref);
        return scanner.nextLine();
    }
    public static String getInput() {
        return getInput(prefix);
    }
    
    public static void waitChar() {
        java.io.Console console = System.console();
        if (console != null) {
            console.readPassword("");
        } else {
            scanner.nextLine();
        }
    }
    
    public static void print(String out) {
        System.out.println(out);
    } 
}

