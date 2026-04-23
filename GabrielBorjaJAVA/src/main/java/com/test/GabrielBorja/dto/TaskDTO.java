package com.test.GabrielBorja.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Boolean completada;

    @JsonFormat(pattern = "dd-MMM-yy")
    private LocalDate fechaCreacion;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaCreacion;
}
