package com.test.GabrielBorja.mapper;

import org.springframework.stereotype.Component;
import com.test.GabrielBorja.model.Task;
import com.test.GabrielBorja.dto.CreateTaskDTO;
import com.test.GabrielBorja.dto.TaskDTO;
import com.test.GabrielBorja.dto.UpdateTaskDTO;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setDescripcion(task.getDescripcion());
        dto.setCompletada(task.getCompletada());
        dto.setFechaCreacion(task.getFechaCreacion());
        dto.setHoraCreacion(task.getHoraCreacion());
        return dto;
    }

    public Task toEntity(CreateTaskDTO dto) {
        if (dto == null) {
            return null;
        }
        return Task.builder()
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .completada(false) // Por defecto false al crear
                .build();
    }

    public void updateEntityFromDto(UpdateTaskDTO dto, Task task) {
        if (dto == null || task == null) {
            return;
        }
        if (dto.getTitulo() != null) {
            task.setTitulo(dto.getTitulo());
        }
        if (dto.getDescripcion() != null) {
            task.setDescripcion(dto.getDescripcion());
        }
        if (dto.getCompletada() != null) {
            task.setCompletada(dto.getCompletada());
        }
    }
}
