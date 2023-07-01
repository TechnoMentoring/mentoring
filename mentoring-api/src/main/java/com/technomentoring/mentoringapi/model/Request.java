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
public class Request {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRequest;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime date;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hour;
    @Column(length = 150, nullable = false)
    private String description;
    @Column(length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_Schedule", nullable = false, foreignKey = @ForeignKey(name = "FK_Request_Schedule"))
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "id_Usuario", nullable = false, foreignKey = @ForeignKey(name = "FK_Request_Usuario"))
    private Usuarios usuarios;
}
