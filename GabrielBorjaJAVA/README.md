# Task Management API - Gabriel Borja

Este proyecto es una API REST robusta desarrollada con **Spring Boot 3** para la gestión eficiente de tareas (To-Do List). Implementa operaciones CRUD completas y permite el seguimiento del estado de las tareas.

## 🚀 Tecnologías y Versiones Requeridas

Para ejecutar este proyecto, asegúrate de tener instaladas las siguientes versiones:

- **Java**: JDK 17 o superior.
- **Maven**: 3.8+ (Se incluye el Maven Wrapper `./mvnw`).
- **Framework**: Spring Boot 3.1.4.
- **Base de Datos**: H2 (Base de datos en memoria para desarrollo y pruebas).
- **Lombok**: 1.18.30 (Requiere habilitar Annotation Processing en tu IDE).
- **MapStruct**: 1.5.5.Final.

## 🛠️ Endpoints de la API

La API está disponible bajo el prefijo `/api/tasks`.

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/tasks` | Obtiene el listado completo de tareas. |
| `GET` | `/api/tasks/{id}` | Obtiene el detalle de una tarea específica por ID. |
| `POST` | `/api/tasks` | Crea una nueva tarea. |
| `PUT` | `/api/tasks/{id}` | Actualiza el nombre o descripción de una tarea existente. |
| `PATCH` | `/api/tasks/{id}/complete` | Marca una tarea como completada. |
| `DELETE` | `/api/tasks/{id}` | Elimina una tarea de forma permanente. |

### Ejemplo de Request Body (POST/PUT)
```json
{
  "nombre": "Estudiar Spring Boot",
  "descripcion": "Completar el módulo de seguridad"
}
```

## 💻 Cómo Ejecutar el Proyecto

Sigue estos pasos para levantar la aplicación localmente:

1. **Clonar el repositorio** (o navegar a la carpeta del proyecto).
2. **Compilar el proyecto**:
   ```bash
   ./mvnw clean install
   ```
3. **Ejecutar la aplicación**:
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Verificar el estado**:
   La aplicación estará disponible en `http://localhost:8080`.

## 🔍 Herramientas de Desarrollo

### Consola H2
Puedes acceder a la consola de la base de datos en memoria para inspeccionar las tablas:
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:taskdb`
- **Usuario**: `sa`
- **Password**: (vacío)

### Pruebas Unitarias
Para ejecutar la suite de pruebas:
```bash
./mvnw test
```

---
*Desarrollado por Gabriel Borja para GAPSI.*
