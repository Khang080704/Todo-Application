package com.example.todoapplication.controller;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("user_id") Long user_id){
        UserDto result = userInfoService.getUserById(user_id);
        if(result == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

}
