package com.backendMarch.demo.Service;

import com.backendMarch.demo.Entity.Author;
import com.backendMarch.demo.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    public void addAuthor(Author author){
        authorRepository.save(author);
    }
    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }
}
