package com.JWT.jwtAuthRoleBased.service;


import com.JWT.jwtAuthRoleBased.entity.ApplicationUser;
import com.JWT.jwtAuthRoleBased.repo.ApplicationUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepo applicationUserRepo ;
    // TODO: here we can use our data base to access the userDetails and then return those User deetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails daoUser = applicationUserRepo.findByuserName("shahzaib");
        System.out.println(daoUser.getUsername());
        System.out.println(daoUser.getAuthorities());
        System.out.println(daoUser.getPassword());
        return new User(daoUser.getUsername(),daoUser.getPassword(),daoUser.getAuthorities());
    }

}
