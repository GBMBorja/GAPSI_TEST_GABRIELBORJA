package com.test.GabrielBorja.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import com.test.GabrielBorja.service.TaskService;
import com.test.GabrielBorja.dto.*;
import com.test.GabrielBorja.repository.TaskRepository;
import com.test.GabrielBorja.model.Task;
import com.test.GabrielBorja.mapper.TaskMapper;
import com.test.GabrielBorja.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    @Override
    public TaskDTO create(CreateTaskDTO dto) {
        log.debug("Inicio create() - CreateTaskDTO={}", dto);
        Task task = mapper.toEntity(dto);
        Task saved = repository.save(task);
        log.info("Tarea creada - id={}, titulo={}", saved.getId(), saved.getTitulo());
        return mapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getById(Long id) {
        log.debug("Inicio getById() - id={}", id);
        Task task = repository.findById(id).orElseThrow(() -> {
            log.warn("Tarea no encontrada en getById - id={}", id);
            return new ResourceNotFoundException("No existe tarea: " + id);
        });
        log.info("Tarea obtenida - id={}, titulo={}", task.getId(), task.getTitulo());
        return mapper.toDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getAll() {
        log.debug("Inicio getAll()");
        List<TaskDTO> result = repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        log.info("Tareas listadas - count={}", result.size());
        return result;
    }

    @Override
    public TaskDTO update(Long id, UpdateTaskDTO dto) {
        log.debug("Inicio update() - id={}, UpdateTaskDTO={}", id, dto);
        Task task = repository.findById(id).orElseThrow(() -> {
            log.warn("Tarea no encontrada en update - id={}", id);
            return new ResourceNotFoundException("No existe tarea: " + id);
        });
        mapper.updateEntityFromDto(dto, task);
        Task updated = repository.save(task);
        log.info("Tarea actualizada - id={}, titulo={}", updated.getId(), updated.getTitulo());
        return mapper.toDTO(updated);
    }

    @Override
    public TaskDTO markAsCompleted(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> {
            log.warn("Tarea no encontrada para toggle - id={}", id);
            return new ResourceNotFoundException("No existe tarea: " + id);
        });
        
        Boolean estadoAnterior = task.getCompletada();
        // Lógica de Toggle: si es null o false -> true, si es true -> false
        boolean currentStatus = estadoAnterior != null && estadoAnterior;
        boolean nuevoEstado = !currentStatus;
        
        task.setCompletada(nuevoEstado);
        Task updated = repository.save(task);
        
        log.info("TOGGLE DETECTADO - ID: {} | Antes: {} | Ahora: {}", id, estadoAnterior, nuevoEstado);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        log.debug("Inicio delete() - id={}", id);
        if (!repository.existsById(id)) {
            log.warn("Intento de borrar tarea inexistente - id={}", id);
            throw new ResourceNotFoundException("No existe tarea: " + id);
        }
        repository.deleteById(id);
        log.info("Tarea borrada - id={}", id);
    }
}
