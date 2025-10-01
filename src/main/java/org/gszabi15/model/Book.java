package org.gszabi15.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter @Getter
public class Book {

    private String id;
    private String title;
    private String author;

    @Override
    public String toString() {
        return id + " - " + title + " (" + author + ")";
    }
}