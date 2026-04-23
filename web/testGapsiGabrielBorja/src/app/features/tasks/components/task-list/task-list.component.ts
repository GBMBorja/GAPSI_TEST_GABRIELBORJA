import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent {
  @Input() tasks: Task[] | null = [];
  @Input() isLoading = false;
  @Output() taskToggled = new EventEmitter<number>();
  @Output() taskDeleted = new EventEmitter<number>();

  /**
   * Delegar evento de tarea toggled
   */
  onTaskToggled(taskId: number): void {
    this.taskToggled.emit(taskId);
  }

  /**
   * Delegar evento de tarea eliminada
   */
  onTaskDeleted(taskId: number): void {
    this.taskDeleted.emit(taskId);
  }

  /**
   * Contar tareas completadas
   */
  getCompletedCount(): number {
    return this.tasks?.filter((task) => task.completada).length || 0;
  }

  /**
   * Contar tareas pendientes
   */
  getPendingCount(): number {
    return this.tasks?.filter((task) => !task.completada).length || 0;
  }
}