package com.test.GabrielBorja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.GabrielBorja.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
