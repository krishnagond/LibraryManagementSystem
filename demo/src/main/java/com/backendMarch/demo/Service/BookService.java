package com.backendMarch.demo.Service;

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
    public void addBook(Book book) throws Exception {
        Author author;
        try {
            author = authorRepository.findById(book.getAuthor().getId()).get();
        }
        catch (Exception e){
            throw new Exception("Author not present");
        }
        List<Book> booksWritten = author.getBooks();
        booksWritten.add(book);

        authorRepository.save(author);
    }
}
