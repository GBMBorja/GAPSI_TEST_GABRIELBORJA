import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { TasksRoutingModule } from './tasks-routing.module';
import { SharedModule } from '../../shared/shared.module';

// Componentes
import { TaskItemComponent } from './components/task-item/task-item.component';
import { TaskFormComponent } from './components/task-form/task-form.component';
import { TaskListComponent } from './components/task-list/task-list.component';
import { TasksPageComponent } from './pages/tasks-page/tasks-page.component';

@NgModule({
  declarations: [
    TaskItemComponent,
    TaskFormComponent,
    TaskListComponent,
    TasksPageComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    TasksRoutingModule
  ]
})
export class TasksModule { }