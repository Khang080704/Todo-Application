package com.example.todoapplication.service;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserInfoService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto getUser(Long user_id){
        Optional<User> user = userRepository.findById(user_id);
        if(user.isPresent()){
            return modelMapper.map(user.get(), UserDto.class);
        }
        else {
            return null;
        }
    }
}
