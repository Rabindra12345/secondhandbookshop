package com.rabindra.login.service;

import org.springframework.stereotype.Service;

import com.rabindra.login.entity.User;
import com.rabindra.login.repo.UserRepository;

@Service
public class UserService {
	 private final UserRepository userRepository;
     public UserService(UserRepository userRepository) {
         this.userRepository = userRepository;
     }
     public boolean login(String username, String password) {
         User user = userRepository.findByUsername(username);
         if(user != null && user.getPassword().equals(password))
             return true;
         return false;
     }
}
