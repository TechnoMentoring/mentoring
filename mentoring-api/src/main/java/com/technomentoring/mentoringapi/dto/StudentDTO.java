package com.technomentoring.mentoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {
    private Integer idStudent;
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
