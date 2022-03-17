package com.JWT.jwtAuthRoleBased;


import com.JWT.jwtAuthRoleBased.dto.TokenResponse;
import com.JWT.jwtAuthRoleBased.dto.UserRequest;
import com.JWT.jwtAuthRoleBased.service.MyUserDetailsService;
import com.JWT.jwtAuthRoleBased.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;


    @GetMapping("/home")
    String check(){
        return "hello i am from home controller " ;
    }

    @PostMapping("/auth")
    ResponseEntity<?> authChecker(@RequestBody  UserRequest userRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.username,userRequest.password)
            );
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect Username and password ",e);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(userRequest.username);
        final String jwtToken  = JwtTokenUtil.generateToken(userDetails);
       return  ResponseEntity.ok(new TokenResponse(jwtToken));
    }
}
