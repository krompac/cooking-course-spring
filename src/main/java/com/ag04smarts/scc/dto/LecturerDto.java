package com.ag04smarts.scc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class LecturerDto {
    private Long id;

    private String firstName;

    private String lastName;

    private Set<Long> courses = new HashSet<>();
}
