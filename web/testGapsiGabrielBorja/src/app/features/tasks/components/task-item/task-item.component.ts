import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-task-item',
  templateUrl: './task-item.component.html',
  styleUrls: ['./task-item.component.scss']
})
export class TaskItemComponent {
  @Input() task!: Task;
  @Output() taskToggled = new EventEmitter<number>();
  @Output() taskDeleted = new EventEmitter<number>();

  /**
   * Emitir evento cuando se marca como completada
   */
  onToggleComplete(): void {
    this.taskToggled.emit(this.task.id);
  }

  /**
   * Emitir evento cuando se elimina
   */
  onDelete(): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta tarea?')) {
      this.taskDeleted.emit(this.task.id);
    }
  }
}