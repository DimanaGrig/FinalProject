package com.ittalents.goodreadsprojectv1.model.repository;

import com.ittalents.goodreadsprojectv1.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    public Optional<Author> findAuthorById(int aid);
    public  boolean existsByFirstNameAndLastName(String firstName, String lastName);
    public boolean existsAuthorById(int aid);
    public void deleteById(int aid);

}
