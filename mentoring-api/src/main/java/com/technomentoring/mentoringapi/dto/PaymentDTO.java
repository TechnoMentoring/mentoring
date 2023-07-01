package com.technomentoring.mentoringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {

    private Integer idPayment;
    @NotNull
    private  Integer idRequest;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String status;
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String amount;
    @Size(min = 3, max = 150)
    private String description;
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String codPayment;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime hour;
}