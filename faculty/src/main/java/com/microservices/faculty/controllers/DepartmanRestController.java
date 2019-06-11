package com.microservices.faculty.controllers;

import com.microservices.faculty.jpa.Departman;
import com.microservices.faculty.jpa.Fakultet;
import com.microservices.faculty.repositories.DepartmanRepository;
import com.microservices.faculty.repositories.FakultetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collection;

@RestController
public class DepartmanRestController {
    @Autowired
    private DepartmanRepository departmanRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FakultetRepository fakultetRepository;


    @GetMapping("test")
    //@ApiOperation(value = "Vraća kolekciju svih departmana iz baze podataka")
    public String getTest(){
        return "success";
    }

    @GetMapping("departman")
    //@ApiOperation(value = "Vraća kolekciju svih departmana iz baze podataka")
    public Collection<Departman> getDepartmani(){
        return departmanRepository.findAll();
    }

    @GetMapping("departman/{id}")
   // @ApiOperation(value = "Vraća departman iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public Departman getDepartman(@PathVariable("id") Integer id){
        return departmanRepository.getOne(id);
    }

    @GetMapping("departmanNaziv/{naziv}")
    //@ApiOperation(value = "Vraća departman iz baze podataka prema prosleđenom nazivu")
    public Collection<Departman> getDepartmanByNaziv(@PathVariable("naziv") String naziv){
        return departmanRepository.findByNazivContainingIgnoreCase(naziv);

    }

    @GetMapping("departmanFakultet/{id}")
    public Collection<Departman> getDepartmanByFakultet(@PathVariable ("id") Integer id){
        Fakultet f = fakultetRepository.getOne(id);
        return departmanRepository.findByFakultet(f);
    }
    //insert
    @CrossOrigin
    @PostMapping("departman")
    //@ApiOperation(value = "Upisuje departman u bazu podataka")
    public ResponseEntity<Departman> insertDepartman (@RequestBody Departman departman){
        if(!departmanRepository.existsById(departman.getId())) {
            departmanRepository.save(departman);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    //update
    @CrossOrigin
    @PutMapping("departman")
   // @ApiOperation(value = "Modifikuje postojeći departman u bazi podataka")
    public ResponseEntity<Departman> updateDepartman(@RequestBody Departman departman){
        if(!departmanRepository.existsById(departman.getId()))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        departmanRepository.save(departman);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //delete
    @CrossOrigin
    @Transactional
    @DeleteMapping("departman/{id}")
   // @ApiOperation(value = "Briše departman iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public ResponseEntity<Departman> deleteDepartman(@PathVariable ("id") Integer id){
        if(!departmanRepository.existsById(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        jdbcTemplate.execute("delete from student where departman = "+id);
        departmanRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
