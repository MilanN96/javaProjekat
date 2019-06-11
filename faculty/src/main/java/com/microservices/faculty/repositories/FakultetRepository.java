package com.microservices.faculty.repositories;

import com.microservices.faculty.jpa.Fakultet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FakultetRepository extends JpaRepository<Fakultet, Integer>{
    Collection<Fakultet> findByNazivContainingIgnoreCase(String naziv);
}
