package com.technomentoring.mentoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
    private Integer idSchedule;
    @NotNull
    private Long idUser;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String title;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime hourStart;
    @NotNull
    private LocalTime hourEnd;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150)
    private String description;
    @Size(min = 3, max = 50)
    private String state;
}
