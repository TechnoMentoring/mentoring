package com.technomentoring.mentoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MentorDTO {
    private Integer idMentor;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String password;
    @NotNull
    @NotEmpty
    @Size(min = 8, max = 8)
    private String DNI;
}
