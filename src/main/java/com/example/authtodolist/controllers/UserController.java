package com.example.authtodolist.controllers;

import com.example.authtodolist.UserValidator;
import com.example.authtodolist.dto.TodoData;
import com.example.authtodolist.models.Task;
import com.example.authtodolist.models.User;
import com.example.authtodolist.services.SecurityService;
import com.example.authtodolist.services.TaskService;
import com.example.authtodolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

        return "redirect:/todolist";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }


    @GetMapping({"/", "/todolist"})
    public String TodoList() {
        return "todolist";
    }

    @RequestMapping(value = "/todolist.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public TodoData getTodoList() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userService.findByUsername(currentPrincipalName);

        TodoData result = new TodoData();

        result.setTasks(taskService.findAllByUser(user));
        result.setSize(taskService.findAll().size());

        return result;
    }

    @RequestMapping(value = "/todolist.json", method = RequestMethod.POST)
    @ResponseBody
    public Task postTodoList(@RequestBody Task task) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userService.findByUsername(currentPrincipalName);

        taskService.save(user, task);

        return task;
    }
}
