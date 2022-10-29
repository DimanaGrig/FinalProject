package com.ittalents.goodreadsprojectv1.services;

import com.ittalents.goodreadsprojectv1.model.entity.*;
import com.ittalents.goodreadsprojectv1.model.exceptions.BadRequestException;
import com.ittalents.goodreadsprojectv1.model.exceptions.NotFoundException;
import com.ittalents.goodreadsprojectv1.model.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ShelfRepository shelfRepository;
    @Autowired
    protected GenreRepository genreRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected ReviewRepository reviewRepository;
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected ModelMapper modelMapper;

    public static final double ADMIN_ID = 1;   //todo - questions on that


    protected User getUserById(int uid) {
        return userRepository.findById(uid).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    protected Genre getGenreById(int id) {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Genre not found!"));
    }

    protected Shelf getShelfById(int id) {
        return shelfRepository.findById(id).orElseThrow(() -> new NotFoundException("Shelf not found!"));
    }

    protected Book getBookByISBN(long isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            return bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException("Shelf not found!"));
        }
    return null;
    }
    protected  Shelf getShelfByName(String name){
        return shelfRepository.getShelfByName(name).orElseThrow(()->new NotFoundException("Shelf not exist!"));

    }
    protected Review getReviewById(int id){
        return reviewRepository.getReviewById(id).orElseThrow(()-> new NotFoundException("This review not exist."));
    }
    protected boolean existByFirstAndLastName(String firstName, String lastName){
        return authorRepository.existsByFirstNameAndLastName(firstName, lastName);
    }

    protected void saveAuthor(Author a){         //todo tova ok li e
        authorRepository.save(a);
    }
    protected Author findAuthorById(int aid){
        return authorRepository.findAuthorById(aid).orElseThrow(()->new BadRequestException("Author not found!"));
    }
    protected boolean existsAuthorById(int aid){
        return authorRepository.existsAuthorById(aid);
    }
    protected Author getAuthorById(int aid){
        return authorRepository.findAuthorById(aid).orElseThrow(()->new NotFoundException("Author not found!"));
    }

    protected  boolean validateName(String name) {
        return (validateLength(name.length()) && validateNoSpecialChars(name));
    }

    protected  boolean validateNoSpecialChars(String string){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        boolean b = m.find();
        if(b){
            return false;
        }
        return true;
    }
    protected  boolean validateLength(int length){
        if(length<=30){
            return true;
        }
        return false;
    }

    protected boolean validateSize(String str){
        if(str.length()>600){
            return false;
        }
        return true;
    }
}

