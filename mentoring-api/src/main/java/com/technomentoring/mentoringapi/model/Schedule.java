package com.technomentoring.mentoringapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Schedule {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSchedule;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hourStart;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hourEnd;

    @Column(length = 150, nullable = false)
    private String description;

    @Column(length = 50)
    private String state;

    @ManyToOne
    @JoinColumn(name = "id_Usuario", foreignKey = @ForeignKey(name = "FK_Schedule_Usuario"))
    private Usuarios usuarios;

}
