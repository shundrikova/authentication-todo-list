package com.example.authtodolist.controllers;

import com.example.authtodolist.models.User;
import com.example.authtodolist.services.SecurityService;
import com.example.authtodolist.services.TaskService;
import com.example.authtodolist.services.UserService;
import com.example.authtodolist.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
  @Autowired private UserService userService;

  @Autowired private TaskService taskService;

  @Autowired private SecurityService securityService;

  @Autowired private UserValidator userValidator;

  @GetMapping("/registration")
  public String registration(Model model) {
    model.addAttribute("userForm", new User());

    return "registration";
  }

  @PostMapping("/registration")
  public String registration(
      @ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
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
    if (error != null) model.addAttribute("error", "Your username and password is invalid.");

    if (logout != null) model.addAttribute("message", "You have been logged out successfully.");

    return "login";
  }

  @GetMapping({"/", "/todolist"})
  public String TodoList() {
    return "todolist";
  }
}
