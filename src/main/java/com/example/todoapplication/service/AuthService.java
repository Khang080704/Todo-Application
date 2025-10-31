package com.example.todoapplication.service;

import com.example.todoapplication.model.AuthUser;
import com.example.todoapplication.model.UserModel;
import com.example.todoapplication.repository.AuthUserRepository;
import com.example.todoapplication.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {
    @Autowired
    private final AuthUserRepository authUserRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(String username, String password, String email, String firstname, String lastname) {
        try {
            AuthUser authUser = new AuthUser();
            authUser.setUsername(username);
            authUser.setPassword(passwordEncoder.encode(password));

            UserModel userModel = new UserModel();
            userModel.setFirstName(firstname);
            userModel.setLastName(lastname);
            userModel.setEmail(email);

            authUser.setUser(userModel);
            authUserRepository.save(authUser);
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }
}
