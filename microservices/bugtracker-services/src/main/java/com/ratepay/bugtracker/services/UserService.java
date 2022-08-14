package com.ratepay.bugtracker.services;

import com.ratepay.bugtracker.exceptions.custom.EntityNotFoundException;
import com.ratepay.bugtracker.repository.UserRepository;
import com.ratepay.client.bugtracker.entities.User;
import com.ratepay.client.bugtracker.mapper.UserMapper;
import com.ratepay.client.bugtracker.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserModel findByUsername(String username) {
        return userMapper.toModel(userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User " + username + " doesn't exist!")));
    }

    public UserModel save(UserModel user) {
        User userSaved = userRepository.save(userMapper.toEntity(user));
       return userMapper.toModel(userSaved);
    }

    public UserModel findById(Long userId) {
        return userMapper.toModel(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist!")));
    }

    public void delete(UserModel user) {
        userRepository.delete(userMapper.toEntity(user));
    }
}
