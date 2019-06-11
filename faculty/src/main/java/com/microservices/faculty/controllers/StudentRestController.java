package com.microservices.faculty.controllers;

import com.microservices.faculty.jpa.Departman;
import com.microservices.faculty.jpa.Student;
import com.microservices.faculty.repositories.DepartmanRepository;
import com.microservices.faculty.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
//@Api(tags = {"Student CRUD operacije"})
public class StudentRestController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DepartmanRepository departmanRepository;

    @GetMapping("student")
  //  @ApiOperation(value = "Vraća kolekciju svih studenata iz baze podataka")
    public Collection<Student> getStudenti(){
        return studentRepository.findAll();
    }

    @GetMapping("studentId/{id}")
    //@ApiOperation(value = "Vraća student iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public Student getStudent(@PathVariable("id") Integer id) {
        return studentRepository.getOne(id);
    }

    @GetMapping("studentiDepartmana/{id}")
    //@ApiOperation(value = "Vraća kolekciju studenta jednog departmana iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public Collection<Student> getStudentiDepartmana(@PathVariable ("id") Integer id){
        Departman d = departmanRepository.getOne(id);
        return studentRepository.findByDepartman(d);
    }

    //insert
    @CrossOrigin
    @PostMapping("student")
    //@ApiOperation(value = "Upisuje studenta u bazu podataka")
    public ResponseEntity<Student> insertStudent(@RequestBody Student student){
        if(studentRepository.existsById(student.getId()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        studentRepository.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //update
    @CrossOrigin
    @PutMapping("student")
    //@ApiOperation(value = "Modifikuje postojećeg studenta u bazi podataka")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        if(!studentRepository.existsById(student.getId()))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        studentRepository.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //delete
    @CrossOrigin
    @DeleteMapping("student/{id}")
    //@ApiOperation(value = "Briše studenta iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") Integer id){
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}