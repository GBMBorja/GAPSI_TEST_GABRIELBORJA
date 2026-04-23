package com.test.GabrielBorja.service;

import com.test.GabrielBorja.dto.CreateTaskDTO;
import com.test.GabrielBorja.dto.TaskDTO;
import com.test.GabrielBorja.dto.UpdateTaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO create(CreateTaskDTO dto);
    TaskDTO getById(Long id);
    List<TaskDTO> getAll();
    TaskDTO update(Long id, UpdateTaskDTO dto);
    TaskDTO markAsCompleted(Long id);
    void delete(Long id);
}
