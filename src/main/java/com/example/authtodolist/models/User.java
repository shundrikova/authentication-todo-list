package com.example.authtodolist.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @NotNull
  @Column(name = "username")
  private String username;

  @NotNull
  @Column(name = "password")
  private String password;

  @OneToMany
  @JoinTable(
      name = "user_tasks",
      joinColumns = @JoinColumn(name = "uid"),
      inverseJoinColumns = @JoinColumn(name = "tid"))
  @JsonManagedReference
  private List<Task> tasks;

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public void addTask(Task task) {
    this.tasks.add(task);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
