import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreateTaskDto } from '../../models/task.model';

@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss']
})
export class TaskFormComponent implements OnInit {
  @Output() taskCreated = new EventEmitter<CreateTaskDto>();
  
  taskForm!: FormGroup;
  isSubmitting = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  /**
   * Inicializar el formulario reactivo
   */
  private initializeForm(): void {
    this.taskForm = this.formBuilder.group({
      titulo: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.minLength(5)]]
    });
  }

  /**
   * Obtener control del formulario
   */
  getControl(name: string) {
    return this.taskForm.get(name);
  }

  /**
   * Enviar el formulario
   */
  onSubmit(): void {
    if (this.taskForm.invalid) {
      this.markFormGroupTouched(this.taskForm);
      return;
    }

    this.isSubmitting = true;
    const newTask: CreateTaskDto = this.taskForm.value;
    
    this.taskCreated.emit(newTask);
    
    // Resetear el formulario después de enviar
    setTimeout(() => {
      this.taskForm.reset();
      this.isSubmitting = false;
    }, 300);
  }

  /**
   * Marcar todos los campos como tocados para mostrar errores
   */
  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach((key) => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }
}