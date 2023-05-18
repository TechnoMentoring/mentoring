package com.technomentoring.mentoringapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private LocalDateTime date;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hourStart;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hourEnd;

    @Column(length = 150, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_Mentor", nullable = false, foreignKey = @ForeignKey(name = "FK_Schedule_Mentor"))
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "id_Student", nullable = false, foreignKey = @ForeignKey(name = "FK_Schedule_Student"))
    private Student student;
}
