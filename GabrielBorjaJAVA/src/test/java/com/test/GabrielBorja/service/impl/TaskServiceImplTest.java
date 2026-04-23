package com.test.GabrielBorja.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.test.GabrielBorja.dto.CreateTaskDTO;
import com.test.GabrielBorja.dto.TaskDTO;
import com.test.GabrielBorja.dto.UpdateTaskDTO;
import com.test.GabrielBorja.exception.ResourceNotFoundException;
import com.test.GabrielBorja.mapper.TaskMapper;
import com.test.GabrielBorja.model.Task;
import com.test.GabrielBorja.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskServiceImpl - Test Unitarios")
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDTO taskDTO;
    private CreateTaskDTO createTaskDTO;
    private UpdateTaskDTO updateTaskDTO;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1L)
                .titulo("Test Task")
                .descripcion("Test Description")
                .completada(false)
                .fechaCreacion(LocalDate.now())
                .horaCreacion(LocalTime.now())
                .build();

        taskDTO = new TaskDTO(1L, "Test Task", "Test Description", false, LocalDate.now(), LocalTime.now());

        createTaskDTO = new CreateTaskDTO("New Task", "New Description");
        updateTaskDTO = new UpdateTaskDTO("Updated Task", "Updated Description", true);
    }

    @Test
    @DisplayName("✅ create() - Debe crear una tarea exitosamente")
    void testCreateSuccess() {
        // Arrange
        when(taskMapper.toEntity(createTaskDTO)).thenReturn(new Task());
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(taskDTO);

        // Act
        TaskDTO result = taskService.create(createTaskDTO);

        // Assert
        assertNotNull(result);
        assertEquals(taskDTO.getId(), result.getId());
        assertEquals(taskDTO.getTitulo(), result.getTitulo());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskMapper, times(1)).toEntity(createTaskDTO);
        verify(taskMapper, times(1)).toDTO(task);
    }

    @Test
    @DisplayName("✅ getById() - Debe obtener una tarea por ID")
    void testGetByIdSuccess() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDTO(task)).thenReturn(taskDTO);

        // Act
        TaskDTO result = taskService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(taskDTO.getId(), result.getId());
        assertEquals(taskDTO.getTitulo(), result.getTitulo());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskMapper, times(1)).toDTO(task);
    }

    @Test
    @DisplayName("❌ getById() - Debe lanzar excepción cuando la tarea no existe")
    void testGetByIdNotFound() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.getById(999L));
        verify(taskRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("✅ getAll() - Debe obtener todas las tareas")
    void testGetAllSuccess() {
        // Arrange
        List<Task> tasks = Arrays.asList(task, task);
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toDTO(any(Task.class))).thenReturn(taskDTO);

        // Act
        List<TaskDTO> result = taskService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
        verify(taskMapper, times(2)).toDTO(any(Task.class));
    }

    @Test
    @DisplayName("✅ getAll() - Debe retornar lista vacía cuando no hay tareas")
    void testGetAllEmpty() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<TaskDTO> result = taskService.getAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("✅ update() - Debe actualizar una tarea exitosamente")
    void testUpdateSuccess() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskMapper).updateEntityFromDto(updateTaskDTO, task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(taskDTO);

        // Act
        TaskDTO result = taskService.update(1L, updateTaskDTO);

        // Assert
        assertNotNull(result);
        assertEquals(taskDTO.getId(), result.getId());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskMapper, times(1)).updateEntityFromDto(updateTaskDTO, task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("❌ update() - Debe lanzar excepción cuando la tarea no existe")
    void testUpdateNotFound() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.update(999L, updateTaskDTO));
        verify(taskRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("✅ delete() - Debe eliminar una tarea exitosamente")
    void testDeleteSuccess() {
        // Arrange
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        // Act
        taskService.delete(1L);

        // Assert
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("❌ delete() - Debe lanzar excepción cuando la tarea no existe")
    void testDeleteNotFound() {
        // Arrange
        when(taskRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.delete(999L));
        verify(taskRepository, times(1)).existsById(999L);
        verify(taskRepository, never()).deleteById(any());
    }
}
