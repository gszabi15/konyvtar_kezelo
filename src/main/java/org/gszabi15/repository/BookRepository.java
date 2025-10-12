package org.gszabi15.repository;

import org.gszabi15.model.entity.Book;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<@NotNull Book, @NotNull String> {
}
