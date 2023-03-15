package com.backendMarch.demo.Service;

import com.backendMarch.demo.DTO.StudentRequestDto;
import com.backendMarch.demo.DTO.StudentResponseDto;
import com.backendMarch.demo.DTO.StudentUpdateEmailRequestDto;
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
    public void addStudent(StudentRequestDto studentRequestDto){
        //set card value
       Student student = new Student();
       student.setAge(studentRequestDto.getAge());
       student.setName(studentRequestDto.getName());
       student.setDepartment(studentRequestDto.getDepartment());
       student.setEmail(studentRequestDto.getEmail());

       //create a card object
        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setStudent(student);
        //set card in student
        student.setCard(card);
         //check
        studentRepository.save(student);
    }
    public String findByEmail(String email){
        Student student = studentRepository.findByEmail(email);
       return student.getName();
    }

    public StudentResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
        Student student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());
        // update step
        Student updatedStudent = studentRepository.save(student);

        // convert updatede student to response dto
        StudentResponseDto studentResponseDto =new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());

        return studentResponseDto;
    }
}
