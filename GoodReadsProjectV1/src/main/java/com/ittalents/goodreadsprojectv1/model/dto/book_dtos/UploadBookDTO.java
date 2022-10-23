package com.ittalents.goodreadsprojectv1.model.dto.book_dtos;

import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorDTO;
import com.ittalents.goodreadsprojectv1.model.dto.author_dtos.AuthorWithoutBooksDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreDTO;
import com.ittalents.goodreadsprojectv1.model.dto.genre_dtos.GenreWithoutBooksDTO;
import lombok.Data;

@Data
public class UploadBookDTO {
    private long ibsn;
    private String name;
    private String content;
    private String additionalInfo;
    private AuthorWithoutBooksDTO author;
    private GenreWithoutBooksDTO genres;


}
