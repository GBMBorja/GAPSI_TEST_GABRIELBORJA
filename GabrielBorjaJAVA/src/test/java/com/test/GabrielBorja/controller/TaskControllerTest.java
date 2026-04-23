package com.test.GabrielBorja.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.GabrielBorja.dto.CreateTaskDTO;
import com.test.GabrielBorja.dto.TaskDTO;
import com.test.GabrielBorja.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@DisplayName("TaskController - Test Unitarios")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    @DisplayName("✅ GET /api/tasks - Debe retornar lista de tareas (200 OK)")
    void testGetAllTasks() throws Exception {
        TaskDTO task = new TaskDTO(1L, "Titulo", "Desc", false, LocalDate.now(), LocalTime.now());
        when(taskService.getAll()).thenReturn(Arrays.asList(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Titulo"));
    }

    @Test
    @DisplayName("✅ GET /api/tasks - Debe retornar 204 No Content si está vacío")
    void testGetAllTasksEmpty() throws Exception {
        when(taskService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("✅ POST /api/tasks - Debe crear una tarea (201 Created)")
    void testCreateTask() throws Exception {
        CreateTaskDTO createDTO = new CreateTaskDTO("Nuevo", "Desc");
        TaskDTO savedDTO = new TaskDTO(1L, "Nuevo", "Desc", false, LocalDate.now(), LocalTime.now());

        when(taskService.create(any(CreateTaskDTO.class))).thenReturn(savedDTO);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Nuevo"));
    }
}