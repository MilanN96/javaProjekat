package com.microservices.faculty.repositories;

import com.microservices.faculty.jpa.Departman;
import com.microservices.faculty.jpa.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Collection<Student> findByDepartman(Departman d);

}