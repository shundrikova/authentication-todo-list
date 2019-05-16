package com.example.authtodolist.services.impl;

import com.example.authtodolist.models.User;
import com.example.authtodolist.repos.UserRepository;
import com.example.authtodolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private UserRepository userRepository;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public void save(User userForm) {
    User user = new User();
    user.setUsername(userForm.getUsername());
    user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
    userRepository.save(user);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
