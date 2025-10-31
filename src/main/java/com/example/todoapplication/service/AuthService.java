package com.example.todoapplication.service;

import com.example.todoapplication.entity.AuthUser;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.AuthUserRepository;
import com.example.todoapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class AuthService {
    @Autowired
    private final CustomUserDetails customUserDetails;
    @Autowired
    private final AuthUserRepository authUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

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

        UserDetails user = userDetailsService.loadUserByUsername(username);
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );

    }
}
