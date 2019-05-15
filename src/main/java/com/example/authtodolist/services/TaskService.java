package com.example.authtodolist.services;

import com.example.authtodolist.models.Task;
import com.example.authtodolist.models.User;

import java.util.List;

public interface TaskService {
    Task findById(long id);

    List<Task> findAllByUser(User user);

    void save(User user, Task task);

    List<Task> findAll();
}
