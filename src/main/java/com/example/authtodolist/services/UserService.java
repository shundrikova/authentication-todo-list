package com.example.authtodolist.services;

import com.example.authtodolist.models.User;

public interface UserService {
  void save(User user);

  User findByUsername(String username);
}
