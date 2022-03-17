package com.JWT.jwtAuthRoleBased.config;


import com.JWT.jwtAuthRoleBased.service.MyUserDetailsService;
import com.JWT.jwtAuthRoleBased.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {



    @Autowired
    private MyUserDetailsService myUserDetailsService ;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException
    {

            final String authHeader   = request.getHeader("Authorization");
             String username  = null;
             String JwtToken =null ;

             if(authHeader != null && authHeader.startsWith("bearer")){
                 JwtToken = authHeader.substring(7);
                 username = JwtTokenUtil.getUsernameFromToken(JwtToken);
             }
             if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ){
                 UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                 if(JwtTokenUtil.validateToken(userDetails,JwtToken)){
                     UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                             = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                     SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                 }

             }

             filterChain.doFilter(request,response);

    }

}
