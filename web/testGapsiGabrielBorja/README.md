# Gapsi Task Manager - Frontend

Este proyecto es la interfaz de usuario para el sistema de gestión de tareas de GAPSI. Ha sido desarrollado con **Angular 17** y ofrece una experiencia reactiva y moderna para la administración de tareas diarias.

## 🚀 Funcionalidades

- **Visualización de Tareas**: Listado dinámico con indicadores de estado (Pendiente/Completada).
- **Gestión de Estado**: Marcado rápido de tareas como completadas mediante una petición parcial (PATCH).
- **Creación y Edición**: Formulario reactivo para añadir nuevas tareas con validaciones.
- **Eliminación**: Capacidad de remover tareas con confirmación de usuario.
- **Estadísticas en Tiempo Real**: Resumen dinámico del total de tareas, completadas y pendientes.

## 🔌 Integración con el Backend

El frontend consume una API REST desarrollada en **Spring Boot**.

### Persistencia y Base de Datos
Para simplificar la ejecución y prueba del proyecto, el backend utiliza una **base de datos en memoria (H2)**. 
- **Ventaja:** No requiere configuración previa de un motor de base de datos externo (como MySQL o PostgreSQL).
- **Nota:** Los datos se reinician cada vez que el servidor backend se detiene.

- **URL Base:** `http://localhost:8080/api/tasks`

### Endpoints Consumidos

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/api/tasks` | Obtiene la lista completa de tareas. |
| `POST` | `/api/tasks` | Crea una nueva tarea (espera `titulo` y `descripcion`). |
| `PUT` | `/api/tasks/{id}` | Actualiza una tarea existente. |
| `PATCH` | `/api/tasks/{id}/complete` | Cambia el estado de `completada` de la tarea. |
| `DELETE` | `/api/tasks/{id}` | Elimina una tarea permanentemente. |

## 🛠️ Requisitos del Sistema

Para ejecutar este proyecto localmente, necesitas tener instalado:

- **Node.js**: Versión 18.x o superior.
- **npm**: Versión 10.x o superior.
- **Angular CLI**: Versión 17.3.x.
- **Backend**: El servidor Spring Boot debe estar en ejecución en el puerto 8080.

## 💻 Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone <url-del-repositorio>
   ```

2. **Instalar dependencias:**
   ```bash
   npm install
   ```

3. **Ejecutar el servidor de desarrollo:**
   ```bash
   ng serve
   ```

4. **Acceder a la aplicación:**
   Abre tu navegador en `http://localhost:4200/`.

## 🧪 Pruebas Unitarias

Ejecuta `ng test` para correr las pruebas unitarias a través de [Karma](https://karma-runner.github.io).

---
Desarrollado por Gabriel Borja para el proceso técnico de GAPSI.

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
