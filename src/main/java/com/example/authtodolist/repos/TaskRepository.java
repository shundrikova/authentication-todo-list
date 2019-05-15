package com.example.authtodolist.repos;

import com.example.authtodolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findById(long id);
}
