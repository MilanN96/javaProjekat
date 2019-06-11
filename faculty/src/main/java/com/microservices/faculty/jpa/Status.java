package com.microservices.faculty.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(includeFieldNames=true)
@NamedQuery(name="Status.findAll", query="SELECT s FROM Status s")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="Status_ID_GENERATOR", sequenceName="Status_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Status_ID_GENERATOR")
    private Integer id;

    private String naziv;

    private String oznaka;

    //bi-directional many-to-one association to Student
    @OneToMany(mappedBy="status")
    @JsonIgnore
    private List<Student> students;
}
