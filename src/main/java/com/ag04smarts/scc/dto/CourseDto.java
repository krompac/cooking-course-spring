package com.ag04smarts.scc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CourseDto {
    private Long id;

    private String name;

    private String type;

    private Integer numberOfStudents;

    private Set<LecturerDto> lecturers = new HashSet<>();
    private Set<RegistrationDto> registrations = new HashSet<>();
}
