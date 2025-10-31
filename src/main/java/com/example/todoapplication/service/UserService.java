package com.example.todoapplication.service;

import com.example.todoapplication.entity.AuthUser;
import com.example.todoapplication.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username);
        if(authUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(authUser);
    }


}
