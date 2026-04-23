package com.test.GabrielBorja;

import com.test.GabrielBorja.dto.CreateTaskDTO;
import com.test.GabrielBorja.dto.TaskDTO;
import com.test.GabrielBorja.dto.UpdateTaskDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("TaskController - Test de Integración")
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateTaskDTO createTaskDTO;
    private UpdateTaskDTO updateTaskDTO;

    @BeforeEach
    void setUp() {
        createTaskDTO = new CreateTaskDTO("Tarea de Integración", "Descripción de test");
        updateTaskDTO = new UpdateTaskDTO("Tarea Actualizada", "Descripción actualizada", true);
    }

    @Test
    @DisplayName("✅ CRUD Completo - Crear, Obtener, Actualizar, Eliminar")
    void testFullCrudFlow() throws Exception {
        // 1. CREATE - Crear una tarea
        MvcResult createResult = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTaskDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.titulo", is("Tarea de Integración")))
                .andExpect(jsonPath("$.descripcion", is("Descripción de test")))
                .andExpect(jsonPath("$.fechaCreacion").isNotEmpty())
                .andExpect(jsonPath("$.horaCreacion").isNotEmpty())
                .andReturn();

        String responseBody = createResult.getResponse().getContentAsString();
        TaskDTO createdTask = objectMapper.readValue(responseBody, TaskDTO.class);
        Long taskId = createdTask.getId();

        // 2. GET BY ID - Obtener la tarea creada
        mockMvc.perform(get("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskId.intValue())))
                .andExpect(jsonPath("$.titulo", is("Tarea de Integración")))
                .andReturn();

        // 3. GET ALL - Listar tareas (debe incluir la nueva)
        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].titulo", hasItem("Tarea de Integración")))
                .andReturn();

        // 4. UPDATE - Actualizar la tarea
        mockMvc.perform(put("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTaskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskId.intValue())))
                .andExpect(jsonPath("$.titulo", is("Tarea Actualizada")))
                .andExpect(jsonPath("$.descripcion", is("Descripción actualizada")))
                .andReturn();

        // 5. GET BY ID - Verificar que la actualización funcionó
        mockMvc.perform(get("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Tarea Actualizada")))
                .andReturn();

        // 6. DELETE - Eliminar la tarea
        mockMvc.perform(delete("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // 7. GET BY ID - Verificar que la tarea fue eliminada (404)
        mockMvc.perform(get("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("✅ Crear múltiples tareas y listarlas")
    void testCreateMultipleTasksAndList() throws Exception {
        // Crear 3 tareas
        for (int i = 1; i <= 3; i++) {
            CreateTaskDTO dto = new CreateTaskDTO("Tarea " + i, "Descripción " + i);
            mockMvc.perform(post("/api/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isCreated());
        }

        // Listar y verificar que hay al menos 3 tareas
        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));
    }

    @Test
    @DisplayName("❌ Validar errores - Campos requeridos")
    void testValidationErrors() throws Exception {
        // Intenta crear con titulo vacío
        CreateTaskDTO invalidDTO1 = new CreateTaskDTO("", "Descripción");
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("❌ Obtener tarea inexistente - 404")
    void testGetNonExistentTask() throws Exception {
        mockMvc.perform(get("/api/tasks/99999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
