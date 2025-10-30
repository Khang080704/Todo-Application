package com.example.todoapplication.service;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.model.UserModel;
import com.example.todoapplication.repository.UserRepository;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper  modelMapper;

    public List<UserDto> getAllUser() {
         List<UserModel> listUser = userRepository.findAll();
         List<UserDto> userDtoList = new ArrayList<>();
         for (UserModel userModel : listUser) {
             UserDto userDto = modelMapper.map(userModel, UserDto.class);
             userDtoList.add(userDto);
         }
         return userDtoList;
    }

    public UserDto getUserById(Long id) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isPresent()) {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto;
        }
        else {
            return null;
        }
    }

    public boolean addNewUser(UserModel user) {
        try {
            userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateUser( Long user_id, UserModel user) {
        Optional<UserModel> userModel = userRepository.findById(user_id);
        if(userModel.isPresent()) {
            UserModel exitsUser =  userModel.get();
            exitsUser.setFirstName(user.getFirstName());
            exitsUser.setLastName(user.getLastName());
            exitsUser.setEmail(user.getEmail());
            userRepository.save(exitsUser);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteUser(Long user_id) {
        Optional<UserModel> isExits =  userRepository.findById(user_id);
        if(isExits.isPresent()) {
            userRepository.deleteById(user_id);
            return true;
        }
        else {
            return false;
        }
    }
}
