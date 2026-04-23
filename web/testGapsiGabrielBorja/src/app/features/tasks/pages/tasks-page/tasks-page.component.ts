import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TaskService } from '../../../../core/services/task.service';
import { Task, CreateTaskDto } from '../../models/task.model';

@Component({
  selector: 'app-tasks-page',
  templateUrl: './tasks-page.component.html',
  styleUrls: ['./tasks-page.component.scss']
})
export class TasksPageComponent implements OnInit, OnDestroy {
  tasks: Task[] = [];
  isLoading = false;
  private destroy$ = new Subject<void>();

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /**
   * Cargar las tareas del servicio
   */
  private loadTasks(): void {
    this.isLoading = true;
    this.taskService.tasks$
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (tasks) => {
          this.tasks = tasks;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error al cargar tareas:', error);
          this.isLoading = false;
          this.showErrorAlert('Error al cargar las tareas');
        }
      });
  }

  /**
   * Crear una nueva tarea
   */
  onCreateTask(taskDto: CreateTaskDto): void {
    this.taskService.createTask(taskDto).subscribe({
      next: (newTask) => {
        console.log('Tarea creada:', newTask);
        this.showSuccessAlert('✅ Tarea creada exitosamente');
      },
      error: (error) => {
        console.error('Error al crear tarea:', error);
        this.showErrorAlert('❌ Error al crear la tarea. Intenta de nuevo.');
      }
    });
  }

  /**
   * Toglear completado de una tarea usando PATCH
   */
  onToggleTask(taskId: number): void {
    this.taskService.toggleTaskComplete(taskId).subscribe({
      next: (updatedTask) => {
        console.log('Tarea toggled:', updatedTask);
        this.showSuccessAlert(
          updatedTask.completada ? '✅ Tarea completada' : '⏳ Tarea marcada como pendiente'
        );
      },
      error: (error) => {
        console.error('Error al toglear tarea:', error);
        this.showErrorAlert('❌ Error al actualizar la tarea.');
      }
    });
  }

  /**
   * Eliminar una tarea
   */
  onDeleteTask(taskId: number): void {
    this.taskService.deleteTask(taskId).subscribe({
      next: () => {
        console.log('Tarea eliminada');
        this.showSuccessAlert('✅ Tarea eliminada');
      },
      error: (error) => {
        console.error('Error al eliminar tarea:', error);
        this.showErrorAlert('❌ Error al eliminar la tarea.');
      }
    });
  }

  /**
   * Mostrar alerta de éxito
   */
  private showSuccessAlert(message: string): void {
    console.log(message);
    // Aquí puedes agregar un servicio de notificaciones en el futuro
  }

  /**
   * Mostrar alerta de error
   */
  private showErrorAlert(message: string): void {
    console.error(message);
    // Aquí puedes agregar un servicio de notificaciones en el futuro
  }
}