package com.JWT.jwtAuthRoleBased.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



public class JwtTokenUtil {

    private static  long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000;
    public static  String SECRET = "mysecret";



    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(Claims::getSubject,token);
    }


    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(Claims::getExpiration,token);
    }


    public static  <T> T getClaimFromToken(Function<Claims, T> claimsResolver,String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    public static String getClaimFromTokenByName(String name,String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return (String)claims.get(name);
    }


    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }


    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public static String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getUsername());
    }


    private static String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }


    public static Boolean validateToken(UserDetails userDetails,String token) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public static Boolean validateToken(String token ) {
        return (!isTokenExpired(token));
    }


}