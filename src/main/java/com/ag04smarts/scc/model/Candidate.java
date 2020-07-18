package com.ag04smarts.scc.model;

import com.ag04smarts.scc.model.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString(exclude = {"registration", "mentor"})
@EqualsAndHashCode(exclude = {"registration", "mentor"})
@Table(name = "Candidate")
public class Candidate extends Person {
    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "picture")
    @Lob
    private Byte[] picture;

    @OneToOne(cascade = CascadeType.ALL)
    private Registration registration;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addMentor(Mentor mentor){
        mentor.getCandidates().add(this);
        this.setMentor(mentor);
    }

    public void addRegistration(Registration registration) {
        registration.setCandidate(this);
        this.setRegistration(registration);
    }
}
