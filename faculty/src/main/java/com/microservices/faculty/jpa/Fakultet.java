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
@NamedQuery(name="Fakultet.findAll", query="SELECT f FROM Fakultet f")
public class Fakultet implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="FAKULTET_ID_GENERATOR", sequenceName="FAKULTET_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FAKULTET_ID_GENERATOR")
    private Integer id;

    private String naziv;

    private String sediste;

    //bi-directional many-to-one association to Departman
    @OneToMany(mappedBy="fakultet")
    @JsonIgnore
    private List<Departman> departmans;
}
