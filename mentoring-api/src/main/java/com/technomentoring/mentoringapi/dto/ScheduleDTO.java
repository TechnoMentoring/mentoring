package com.technomentoring.mentoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
    private Integer idSchedule;
    @NotNull
    private Integer idStudent;
    @NotNull
    private Integer idMentor;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String title;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private LocalTime hourStart;
    @NotNull
    private LocalTime hourEnd;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150)
    private String description;
}
