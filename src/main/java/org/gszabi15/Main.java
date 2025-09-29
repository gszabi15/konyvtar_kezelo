package org.gszabi15;

import org.gszabi15.controller.BookController;
import org.gszabi15.controller.Controller;
import org.gszabi15.repository.BookRepository;
import org.gszabi15.service.BookService;
import org.gszabi15.view.View;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        final View view = new View();
        final BookController bookController = new BookController( new BookService( new BookRepository() ) );

        final Controller controller = new Controller(bookController, view);

        controller.ViewController();

    }
}