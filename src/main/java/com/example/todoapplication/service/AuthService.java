package com.example.todoapplication.service;

import com.example.todoapplication.entity.AuthUser;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AuthUserRepository aUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authUserRepository = aUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public boolean registerUser(String username, String password, String email, String firstname, String lastname) {
        try {
            AuthUser authUser = new AuthUser();
            authUser.setUsername(username);
            authUser.setPassword(passwordEncoder.encode(password));

            User user = new User();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setEmail(email);

            authUser.setUser(user);
            authUserRepository.save(authUser);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Map<String, String> login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        AuthUser authUser = authUserRepository.findByUsername(username);

        String accessToken = jwtService.generateAccessToken(authUser.getUsername(), authUser.getUser().getId());
        String refreshToken = jwtService.generateRefreshToken(authUser.getUsername(), authUser.getUser().getId());
        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );

    }

    public String getAccessTokenFromRefreshToken(String refreshToken) {
        String newAccessToken = jwtService.getAccessTokenFromRefreshToken(refreshToken);
        return newAccessToken;
    }
}
