package com.example.todoapplication.service;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.entity.AuthUser;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<User> isUserExits = userRepository.findById(customUserDetails.getUserInfo().getId());
        return isUserExits.orElse(null);
    }

    public boolean updateUserInfo(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<User> isUserExits = userRepository.findById(customUserDetails.getUserInfo().getId());
        if(isUserExits.isPresent()) {
            User userExists = isUserExits.get();
            userExists.setEmail(user.getEmail());
            userExists.setFirstName(user.getFirstName());
            userExists.setLastName(user.getLastName());
            userRepository.save(userExists);
            return true;
        }
        else {
            return false;
        }
    }
}
