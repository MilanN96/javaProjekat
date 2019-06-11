package com.microservices.faculty.repositories;

import com.microservices.faculty.jpa.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Collection<Status> findByNazivContainingIgnoreCase(String naziv);
}
