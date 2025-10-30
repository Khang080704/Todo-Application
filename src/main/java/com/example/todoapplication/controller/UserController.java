package com.example.todoapplication.controller;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.model.UserModel;
import com.example.todoapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity getAllUsers() {
        List<UserDto> list = userService.getAllUser();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserModel userModel) {
        final boolean isSuccess = userService.addNewUser(userModel);
        if (isSuccess) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(500).body("Error");
        }
    }

    @GetMapping("{user_id}")
    public ResponseEntity getUserById(@PathVariable("user_id") Long user_id) {
        UserDto userDto = userService.getUserById(user_id);
        if(userDto != null) {
            return ResponseEntity.ok().body(userDto);
        }
        else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PutMapping("{user_id}")
    public ResponseEntity updateUser(@PathVariable("user_id") Long user_id, @RequestBody UserModel userModel) {
        final boolean updateSuccess = userService.updateUser(user_id ,userModel);
        if(updateSuccess) {
            return ResponseEntity.ok().body("User updated successfully");
        }
        else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @DeleteMapping("{user_id}")
    public ResponseEntity deleteUser(@PathVariable("user_id") Long user_id) {
        boolean deleteSuccess = userService.deleteUser(user_id);
        if(deleteSuccess) {
            return ResponseEntity.ok().body("User deleted successfully");
        }
        else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
