/**
 * Modelo de datos para una Tarea (coincide con el backend Java)
 */
export interface Task {
  id: number;
  titulo: string;
  descripcion: string | null;
  completada: boolean;
  fechaCreacion: string;
  horaCreacion: string;
}

/**
 * DTO para crear una nueva tarea
 */
export interface CreateTaskDto {
  titulo: string;
  descripcion?: string;
}

/**
 * DTO para actualizar una tarea
 */
export interface UpdateTaskDto {
  titulo?: string;
  descripcion?: string;
  completada?: boolean;
}