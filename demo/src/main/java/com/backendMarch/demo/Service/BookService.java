package com.backendMarch.demo.Service;

import com.backendMarch.demo.DTO.BookRequestDto;
import com.backendMarch.demo.DTO.BookResponseDto;
import com.backendMarch.demo.Entity.Author;
import com.backendMarch.demo.Entity.Book;
import com.backendMarch.demo.Repository.AuthorRepository;
import com.backendMarch.demo.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    AuthorRepository authorRepository;
    public BookResponseDto addBook(BookRequestDto bookRequestDto) throws Exception {
       //get author object
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();
        Book book = new Book();
        book.setTitle(book.getTitle());
        book.setGenre(book.getGenre());
        book.setPrice(book.getPrice());
        book.setIssued(false);
        book.setAuthor(author);

        author.getBooks().add(book);
        authorRepository.save(author);
        //create a response also
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setPrice(book.getPrice());
        return bookResponseDto;
    }
}
