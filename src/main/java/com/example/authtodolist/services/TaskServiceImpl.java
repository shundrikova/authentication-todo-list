package com.example.authtodolist.services;

import com.example.authtodolist.models.Task;
import com.example.authtodolist.models.User;
import com.example.authtodolist.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task findById(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAllByUser(User user) {
        return user.getTasks();
    }

    @Override
    public void save(User user, Task task) {
        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
