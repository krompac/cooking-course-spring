package com.ag04smarts.scc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Data
@Entity
@ToString(exclude = "candidates")
@EqualsAndHashCode(exclude = "candidates")
@Table(name = "Mentor")
public class Mentor extends Person {
    @OneToMany(mappedBy = "mentor")
    private Set<Candidate> candidates = new HashSet<>();

    @Column(name = "age")
    private Integer age;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
