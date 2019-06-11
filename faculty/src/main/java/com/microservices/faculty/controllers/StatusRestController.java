package com.microservices.faculty.controllers;

import com.microservices.faculty.jpa.Status;
import com.microservices.faculty.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collection;

@RestController
//@Api(tags = {"Status CRUD operacije"})
public class StatusRestController {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("status")
    //@ApiOperation(value = "Vraća kolekciju svih statusa iz baze podataka")
    public Collection<Status> getStatusi(){
        return statusRepository.findAll();
    }

    @GetMapping("statusId/{id}")
    //@ApiOperation(value = "Vraća status iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public Status getStatus(@PathVariable("id") Integer id) {
        return statusRepository.getOne(id);
    }

    @GetMapping("statusNaziv/{naziv}")
    //@ApiOperation(value = "Vraća status iz baze podataka prema prosleđenom nazivu")
    public Collection<Status> getStatusByNaziv(@PathVariable("naziv") String naziv){
        return statusRepository.findByNazivContainingIgnoreCase(naziv);
    }

    //insert
    @CrossOrigin
    @PostMapping("status")
    //@ApiOperation(value = "Upisuje status u bazu podataka")
    public ResponseEntity<Status> insertStatus(@RequestBody Status status){
        if(!statusRepository.existsById(status.getId())) {
            statusRepository.save(status);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    //update
    @CrossOrigin
    @PutMapping("status")
    //@ApiOperation(value = "Modifikuje postojeći status u bazi podataka")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status){
        if(statusRepository.existsById(status.getId())) {
            statusRepository.save(status);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //delete
    @CrossOrigin
    @Transactional
    @DeleteMapping("status/{id}")
   // @ApiOperation(value = "Briše status iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public ResponseEntity<HttpStatus> deleteStatus(@PathVariable("id") Integer id){
        if(statusRepository.existsById(id)) {
            jdbcTemplate.execute("delete from student where status="+id);
            statusRepository.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}