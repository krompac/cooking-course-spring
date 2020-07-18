package com.ag04smarts.scc.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class Person extends BaseEntity{
    protected String firstName;
    protected String lastName;
}