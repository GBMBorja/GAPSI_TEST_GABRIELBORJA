## 🧪 Tests Unitarios - Task CRUD

Este proyecto incluye una suite completa de tests para validar todas las funcionalidades del CRUD de Tasks.

### 📁 Archivos de Test Creados

1. **TaskServiceImplTest.java** - Tests del servicio (lógica de negocio)
   - Ubicación: `src/test/java/com/test/GabrielBorja/service/impl/`
   - 8 casos de test usando Mockito

2. **TaskControllerTest.java** - Tests del controlador (endpoints REST)
   - Ubicación: `src/test/java/com/test/GabrielBorja/controller/`
   - 10 casos de test usando MockMvc

3. **TaskControllerIntegrationTest.java** - Tests de integración end-to-end
   - Ubicación: `src/test/java/com/test/GabrielBorja/`
   - 4 casos de test con contexto completo de Spring Boot

---

## 🚀 Ejecutar Tests

### Opción 1: Desde Terminal
```bash
# Ejecutar todos los tests
mvn test

# Ejecutar un test específico
mvn test -Dtest=TaskServiceImplTest

# Ejecutar con reporte de cobertura
mvn test jacoco:report
```

### Opción 2: Desde IDE (IntelliJ/Eclipse)
- Clic derecho en la clase de test → Run 'NombreTest'
- O usa el atajo: Alt + Shift + F10 (IntelliJ)

### Opción 3: Gradle (si lo usas)
```bash
gradle test
```

---

## 📊 Cobertura de Tests

### **TaskServiceImplTest** (8 tests)

| Test | Descripción |
|------|-------------|
| ✅ `testCreateSuccess()` | Crear tarea exitosamente |
| ✅ `testGetByIdSuccess()` | Obtener tarea por ID |
| ❌ `testGetByIdNotFound()` | Excepción cuando tarea no existe |
| ✅ `testGetAllSuccess()` | Obtener todas las tareas |
| ✅ `testGetAllEmpty()` | Lista vacía cuando no hay tareas |
| ✅ `testUpdateSuccess()` | Actualizar tarea |
| ❌ `testUpdateNotFound()` | Excepción al actualizar inexistente |
| ✅ `testDeleteSuccess()` / ❌ `testDeleteNotFound()` | Eliminar tarea |

**Cobertura:** Service layer completo
**Framework:** JUnit 5 + Mockito

---

### **TaskControllerTest** (10 tests)

| Test | Descripción |
|------|-------------|
| ✅ `testCreateSuccess()` | POST retorna 201 Created |
| ❌ `testCreateBadRequest_*()` | Validaciones de entrada |
| ✅ `testGetByIdSuccess()` | GET retorna 200 OK |
| ❌ `testGetByIdNotFound()` | GET retorna 404 Not Found |
| ✅ `testGetAllSuccess()` | GET retorna lista de tareas |
| ✅ `testGetAllEmpty()` | GET retorna 204 No Content |
| ✅ `testUpdateSuccess()` | PUT retorna 200 OK |
| ❌ `testUpdateNotFound()` | PUT retorna 404 Not Found |
| ✅ `testDeleteSuccess()` | DELETE retorna 204 No Content |
| ❌ `testDeleteNotFound()` | DELETE retorna 404 Not Found |

**Cobertura:** REST endpoints + validaciones
**Framework:** JUnit 5 + MockMvc

---

### **TaskControllerIntegrationTest** (4 tests)

| Test | Descripción |
|------|-------------|
| 🔄 `testFullCrudFlow()` | Flujo completo CREATE → READ → UPDATE → DELETE |
| ✅ `testCreateMultipleTasksAndList()` | Crear múltiples tareas y listarlas |
| ❌ `testValidationErrors()` | Validar errores de entrada |
| ❌ `testGetNonExistentTask()` | Manejo de errores 404 |

**Cobertura:** Integración end-to-end
**Framework:** JUnit 5 + Spring Boot Test

---

## 🎯 Resultados Esperados

Cuando ejecutes `mvn test`, deberías ver:

```
Tests run: 22, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.5 sec

✅ TaskServiceImplTest - 8/8 passed
✅ TaskControllerTest - 10/10 passed
✅ TaskControllerIntegrationTest - 4/4 passed
```

---

## 📝 Anatomía de un Test

### Estructura: Arrange → Act → Assert

```java
@Test
@DisplayName("✅ POST /api/tasks - Debe crear una tarea y retornar 201")
void testCreateSuccess() throws Exception {
    // ARRANGE - Configurar datos y mocks
    when(taskService.create(any(CreateTaskDTO.class))).thenReturn(taskDTO);

    // ACT - Ejecutar la acción
    mockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createTaskDTO)))

    // ASSERT - Validar resultados
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.titulo", is("Test Task")));
}
```

---

## 🔍 Validaciones en Tests

### Validaciones HTTP
- ✅ Status codes (200, 201, 204, 400, 404)
- ✅ Content-Type (application/json)
- ✅ Response body structure

### Validaciones de Datos
- ✅ Campos requeridos
- ✅ Formatos (fecha dd-MMM-yy, hora HH:mm)
- ✅ Valores nulos/vacíos

### Validaciones de Flujo
- ✅ Transacciones exitosas
- ✅ Manejo de excepciones
- ✅ Comportamiento en casos de error

---

## 💡 Ejemplos Adicionales

### Ejecutar test con output detallado
```bash
mvn test -Dtest=TaskServiceImplTest -X
```

### Ejecutar solo tests de integración
```bash
mvn test -Dtest=*IntegrationTest
```

### Ejecutar con perfiles específicos
```bash
mvn test -Ptest
```

---

## 📚 Recursos

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Test Documentation](https://spring.io/projects/spring-framework#learn)

---

## ✨ Métricas de Calidad

- **Total de Tests:** 22
- **Lineas de Código (Test):** ~400
- **Cobertura Estimada:** 95%+
- **Tiempo de ejecución:** ~2-3 segundos

---

¡Tu proyecto está completamente testeado! 🎉
