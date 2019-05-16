package com.example.authtodolist.models;


import com.example.authtodolist.dto.TaskData;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "task", schema = "public")
public class Task {

    public Task() {
        super();
    }

    public Task(TaskData tsk, User usr) {
        this.body = tsk.getBody();
        this.color = tsk.getColor();
        this.status = tsk.getStatus();
        this.user = usr;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "body")
    private String body;

    @NotNull
    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinTable(
            name = "user_tasks",
            joinColumns = @JoinColumn(name = "tid"),
            inverseJoinColumns = @JoinColumn(name = "uid"))
    @JsonBackReference
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
