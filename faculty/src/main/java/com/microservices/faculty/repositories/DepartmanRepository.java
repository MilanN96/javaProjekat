package com.microservices.faculty.repositories;

import com.microservices.faculty.jpa.Departman;
import com.microservices.faculty.jpa.Fakultet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DepartmanRepository extends JpaRepository<Departman, Integer> {
    Collection<Departman> findByNazivContainingIgnoreCase(String naziv);
    Collection<Departman> findByFakultet(Fakultet f);
}
