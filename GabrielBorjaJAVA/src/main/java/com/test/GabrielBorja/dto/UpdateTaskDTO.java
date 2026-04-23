package com.test.GabrielBorja.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDTO {
    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    private String descripcion;  // Opcional
    private Boolean completada;
}
