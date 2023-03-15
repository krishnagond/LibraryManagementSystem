package com.backendMarch.demo.Controller;

import com.backendMarch.demo.DTO.BookRequestDto;
import com.backendMarch.demo.DTO.BookResponseDto;
import com.backendMarch.demo.Entity.Book;
import com.backendMarch.demo.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    @PostMapping("/add")
    public BookResponseDto addBook(@RequestBody BookRequestDto bookRequestDto) throws Exception {
        return bookService.addBook(bookRequestDto);
    }
}
