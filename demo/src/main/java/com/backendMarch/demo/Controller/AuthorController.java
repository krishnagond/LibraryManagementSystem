package com.backendMarch.demo.Controller;

import com.backendMarch.demo.Entity.Author;
import com.backendMarch.demo.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @PostMapping("/add")
    public String addAuthor(@RequestBody Author author){
        authorService.addAuthor(author);
        return "Author added successfully";
    }
    @GetMapping("/get_authors")
    public List<Author> getAuthor(){
        return authorService.getAuthors();
    }
}
