package com.example.todoapplication.controller;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.UserRepository;
import com.example.todoapplication.service.CustomUserDetails;
import com.example.todoapplication.service.UserInfoService;
import com.example.todoapplication.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<?> getUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUserInfo();
        return ResponseEntity.ok().body(modelMapper.map(user, UserDto.class));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User newUserInfo){
        final boolean isUpdateSuccess = userInfoService.updateUserInfo(newUserInfo);
        if(isUpdateSuccess){
            return ResponseEntity.ok().body("Update Success");
        }
        else {
            return  ResponseEntity.internalServerError().body("Update Failed");
        }

    }

}
