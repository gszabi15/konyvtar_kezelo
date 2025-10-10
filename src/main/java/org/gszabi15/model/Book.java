package org.gszabi15.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class Book {
    @Id
    private String id;
    private String title;
    private String author;

    private boolean available = true;

    @Override
    public String toString() {
        return id + " - " + title + " (" + author + ")";
    }
}