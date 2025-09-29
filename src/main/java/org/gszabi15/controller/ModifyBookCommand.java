package org.gszabi15.controller;

import org.gszabi15.model.BookDto;
import org.gszabi15.view.AddBookConfirm;
import org.gszabi15.view.EditBook;
import org.gszabi15.view.InputField;
import org.gszabi15.view.View;

public class ModifyBookCommand implements Controller.Command {
    private final BookController bookController;

    public ModifyBookCommand(BookController bookController) {
        this.bookController = bookController;
    }

    @Override
    public void execute() {
        InputField input = new InputField("== Könyv keresése cím, író vagy ID alapján ==", "Keresés: ");
        input.show();
        String find = input.readInput();

        BookDto book = bookController.getAllBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(find.toLowerCase())
                        || b.getAuthor().toLowerCase().contains(find.toLowerCase())
                        || b.getId().equals(find))
                .findFirst()
                .orElse(null);

        if (book == null) return;

        EditBook edit = new EditBook();
        edit.id = book.getId();
        edit.title = book.getTitle();
        edit.author = book.getAuthor();

        while (true) {
            View.clearConsole();
            edit.show();

            switch (View.getInput()) {
                case "0" -> { return; }
                case "1" -> edit.title = readField("Könyv címe: ");
                case "2" -> edit.author = readField("Könyv írója: ");
                case "3" -> {
                    if (confirmChanges(edit)) {
                        bookController.updateBook(book.getId(),
                                new BookDto(edit.id, edit.title, edit.author));
                        return;
                    }
                }
            }
        }
    }

    private String readField(String label) {
        InputField input = new InputField("================ Könyv módosítása ================", label);
        input.show();
        return input.readInput();
    }

    private boolean confirmChanges(EditBook edit) {
        AddBookConfirm confirm = new AddBookConfirm(edit.id, edit.title, edit.author);
        confirm.show();
        return "1".equals(confirm.readInput());
    }
}
