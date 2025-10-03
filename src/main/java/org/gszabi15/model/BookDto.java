package org.gszabi15.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Component
@Scope("prototype")
public class BookDto {

    private String id;
    private String title;
    private String author;

    @Override
    public String toString() {
        return id + " - " + title + " (" + author + ")";
    }


}