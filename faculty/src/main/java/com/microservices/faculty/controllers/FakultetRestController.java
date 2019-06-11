package com.microservices.faculty.controllers;

import com.microservices.faculty.jpa.Fakultet;
import com.microservices.faculty.repositories.FakultetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collection;

@RestController
//@Api(tags = {"Fakultet CRUD operacije"})
public class FakultetRestController {
    @Autowired
    private FakultetRepository fakultetRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("fakultet")
   // @ApiOperation(value = "Vraća kolekciju svih fakulteta iz baze podataka")
    public Collection<Fakultet> getFakulteti(){
        return fakultetRepository.findAll();
    }

    @GetMapping("fakultetId/{id}")
    //@ApiOperation(value = "Vraća fakultet iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public Fakultet getFakultet(@PathVariable("id") Integer id) {
        return fakultetRepository.getOne(id);
    }

    @GetMapping("fakultetNaziv/{naziv}")
    //@ApiOperation(value = "Vraća fakultet iz baze podataka prema prosleđenom nazivu")
    public Collection<Fakultet> getFakultetByName(@PathVariable("naziv") String naziv) {
        return fakultetRepository.findByNazivContainingIgnoreCase(naziv);
    }

    @CrossOrigin
    @PostMapping("fakultet")
    //@ApiOperation(value = "Upisuje fakultet u bazu podataka")
    public ResponseEntity<HttpStatus> insertFakultet(@RequestBody Fakultet fakultet){
        if(fakultetRepository.existsById(fakultet.getId()))
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        fakultetRepository.save(fakultet);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("fakultet")
    //@ApiOperation(value = "Modifikuje postojeći fakultet u bazi podataka")
    public ResponseEntity<HttpStatus> updateFakultet(@RequestBody Fakultet fakultet){
        if(fakultetRepository.existsById(fakultet.getId())) {
            fakultetRepository.save(fakultet);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin
    @Transactional
    @DeleteMapping("fakultet/{id}")
    //@ApiOperation(value = "Briše fakultet iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
    public ResponseEntity<HttpStatus> deleteFakultet(@PathVariable("id") Integer id){
        if(fakultetRepository.existsById(id))
        {
            jdbcTemplate.execute("delete from student where departman in (select id from departman where fakultet ="+id+")");
            jdbcTemplate.execute("delete from departman where fakultet =" + id);
            fakultetRepository.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}