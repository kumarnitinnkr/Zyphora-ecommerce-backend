package com.zyphora.user.service;

import com.zyphora.auth.entity.User;
import com.zyphora.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}