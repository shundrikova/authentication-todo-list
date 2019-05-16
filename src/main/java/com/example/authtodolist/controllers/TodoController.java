package com.example.authtodolist.controllers;

import com.example.authtodolist.dto.TaskData;
import com.example.authtodolist.dto.TodoData;
import com.example.authtodolist.models.User;
import com.example.authtodolist.services.TaskService;
import com.example.authtodolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/todolist.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TodoData getTodoList() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userService.findByUsername(currentPrincipalName);

        TodoData result = new TodoData();

        result.setTasks(taskService.findAllByUser(user));

        return result;
    }

    @RequestMapping(value = "/todolist.json", method = RequestMethod.POST)
    @ResponseBody
    public TaskData addTask(@RequestBody TaskData td) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userService.findByUsername(currentPrincipalName);

        taskService.create(td, user);

        return td;
    }

    @RequestMapping(value = "/todolist.json/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> updateTask(
            @PathVariable("id") long id,
            @RequestBody TaskData td) {

        taskService.update(id, td);

        return new ResponseEntity<>("Task was updated", HttpStatus.OK);
    }
}
