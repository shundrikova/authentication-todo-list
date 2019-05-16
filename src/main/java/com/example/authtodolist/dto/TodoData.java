package com.example.authtodolist.dto;

import com.example.authtodolist.models.Task;

import java.util.List;

public class TodoData {

    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
