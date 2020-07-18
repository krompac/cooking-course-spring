package com.ag04smarts.scc.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Timestamp creationTimeStamp;

    @PrePersist
    private void creationTime(){
        this.setCreationTimeStamp(new Timestamp(new Date().getTime()));
    }
}
