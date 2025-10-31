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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UserDto getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto;
        }
        return null;
    }
}
