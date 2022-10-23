package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.Author;
import com.ittalents.goodreadsprojectv1.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {

    public Optional<Book> findByIsbn(long isbn);
    public List<Book> findByName(String name);
    public Book existsBookByIsbn(Long isbn);

    //get by title and author(FK)
    //get by author (FK)
    //get by language (FK)
    //get by genre (FK)
    //get all other editions for same book (diff IBSN)

}
