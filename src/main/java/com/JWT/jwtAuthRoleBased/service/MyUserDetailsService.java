package com.JWT.jwtAuthRoleBased.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    // TODO: here we can use our data base to access the userDetails and then return those User deetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("shahzaib","123",new ArrayList<>());
    }

}
