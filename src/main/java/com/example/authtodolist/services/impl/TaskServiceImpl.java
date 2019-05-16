package com.example.authtodolist.services.impl;

import com.example.authtodolist.dto.TaskData;
import com.example.authtodolist.models.Task;
import com.example.authtodolist.models.User;
import com.example.authtodolist.repos.TaskRepository;
import com.example.authtodolist.services.TaskService;
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
    public void update (long id, TaskData task) {
        Task taskToUpdate =  taskRepository.findById(id);
        taskToUpdate.setBody(task.getBody());
        taskToUpdate.setColor(task.getColor());
        taskToUpdate.setStatus(task.getStatus());
        taskRepository.save(taskToUpdate);
    }

    @Override
    public void create (TaskData task, User user) {
        Task taskToCreate = new Task(task, user);
        taskRepository.save(taskToCreate);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

}
