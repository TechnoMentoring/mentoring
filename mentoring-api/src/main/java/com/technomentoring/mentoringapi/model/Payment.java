package com.technomentoring.mentoringapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Payment {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPayment;
    @Column(length = 50, nullable = false)
    private String status;
    @Column(length = 50)
    private String amount;
    @Column(length = 50, nullable = false)
    private String description;
    @Column(length = 50, nullable = false)
    private String codPayment;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hour;

    @ManyToOne
    @JoinColumn(name = "id_Request", nullable = false, foreignKey = @ForeignKey(name = "FK_Payment_Request"))
    private Request request;}