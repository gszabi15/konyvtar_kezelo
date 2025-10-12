package org.gszabi15.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class BookDto {
    private String id;
    private String title;
    private String author;
    private boolean available;

    @Override
    public String toString() {
        return id + " - " + title + " (" + author + ")";
    }


}