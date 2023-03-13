package com.backendMarch.demo.Service;

import com.backendMarch.demo.Entity.LibraryCard;
import com.backendMarch.demo.Entity.Student;
import com.backendMarch.demo.Enum.CardStatus;
import com.backendMarch.demo.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public void addStudent(Student student){
        //set card value
        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setValidTill("03/2025");
        card.setStudent(student);

        //set the card atribute in student
        student.setCard(card);

        studentRepository.save(student);
    }
}
