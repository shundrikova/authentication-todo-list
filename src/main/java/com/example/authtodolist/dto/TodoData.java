package com.example.authtodolist.dto;

import com.example.authtodolist.models.Task;

import java.util.List;

public class TodoData {

    private List<Task> tasks;

    private long size;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
