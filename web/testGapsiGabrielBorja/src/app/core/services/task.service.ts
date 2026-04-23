import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Task, CreateTaskDto, UpdateTaskDto } from '../../features/tasks/models/task.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  
  // URL base del backend en Java
  private readonly API_URL = 'http://localhost:8080/api/tasks';
  
  // BehaviorSubject para gestionar la lista de tareas
  private tasksSubject = new BehaviorSubject<Task[]>([]);
  public tasks$ = this.tasksSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadTasks();
  }

  /**
   * Cargar todas las tareas del backend
   */
  loadTasks(): void {
    this.http.get<Task[]>(this.API_URL).subscribe({
      next: (tasks) => {
        this.tasksSubject.next(tasks || []);
      },
      error: (error) => {
        console.error('Error al cargar tareas:', error);
        this.tasksSubject.next([]);
      }
    });
  }

  /**
   * Obtener todas las tareas como Observable
   */
  getTasks(): Observable<Task[]> {
    return this.tasks$;
  }

  /**
   * Crear una nueva tarea
   */
  createTask(taskDto: CreateTaskDto): Observable<Task> {
    return new Observable((observer) => {
      this.http.post<Task>(this.API_URL, taskDto).subscribe({
        next: (newTask) => {
          const currentTasks = this.tasksSubject.value;
          this.tasksSubject.next([...currentTasks, newTask]);
          observer.next(newTask);
          observer.complete();
        },
        error: (error) => {
          console.error('Error al crear tarea:', error);
          observer.error(error);
        }
      });
    });
  }

  /**
   * Marcar una tarea como completada/incompleta usando PATCH
   */
  toggleTaskComplete(id: number): Observable<Task> {
    return new Observable((observer) => {
      this.http.patch<Task>(`${this.API_URL}/${id}/complete`, {}).subscribe({
        next: (updatedTask) => {
          const currentTasks = this.tasksSubject.value;
          const index = currentTasks.findIndex((t) => t.id === id);
          if (index !== -1) {
            currentTasks[index] = updatedTask;
            this.tasksSubject.next([...currentTasks]);
          }
          observer.next(updatedTask);
          observer.complete();
        },
        error: (error) => {
          console.error('Error al toglear tarea:', error);
          observer.error(error);
        }
      });
    });
  }

  /**
   * Actualizar una tarea existente
   */
  updateTask(id: number, taskDto: UpdateTaskDto): Observable<Task> {
    return new Observable((observer) => {
      this.http.put<Task>(`${this.API_URL}/${id}`, taskDto).subscribe({
        next: (updatedTask) => {
          const currentTasks = this.tasksSubject.value;
          const index = currentTasks.findIndex((t) => t.id === id);
          if (index !== -1) {
            currentTasks[index] = updatedTask;
            this.tasksSubject.next([...currentTasks]);
          }
          observer.next(updatedTask);
          observer.complete();
        },
        error: (error) => {
          console.error('Error al actualizar tarea:', error);
          observer.error(error);
        }
      });
    });
  }

  /**
   * Eliminar una tarea
   */
  deleteTask(id: number): Observable<void> {
    return new Observable((observer) => {
      this.http.delete<void>(`${this.API_URL}/${id}`).subscribe({
        next: () => {
          const currentTasks = this.tasksSubject.value;
          const filteredTasks = currentTasks.filter((t) => t.id !== id);
          this.tasksSubject.next(filteredTasks);
          observer.next();
          observer.complete();
        },
        error: (error) => {
          console.error('Error al eliminar tarea:', error);
          observer.error(error);
        }
      });
    });
  }
}