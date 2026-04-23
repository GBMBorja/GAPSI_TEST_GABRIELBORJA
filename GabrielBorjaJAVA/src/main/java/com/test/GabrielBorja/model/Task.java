package com.test.GabrielBorja.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column(nullable = false)
    @Builder.Default
    private Boolean completada = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate fechaCreacion;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalTime horaCreacion;
}
