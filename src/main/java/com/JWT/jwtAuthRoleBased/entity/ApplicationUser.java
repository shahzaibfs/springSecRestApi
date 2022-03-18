package com.JWT.jwtAuthRoleBased.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "applicationUser")
public class ApplicationUser implements UserDetails {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId ;

    @Column(name = "userName")
    private String userName ;

    @Column(name = "password")
    private String Password ;

    @Column(name = "isAccountNonExpired")
    private boolean isAccountNonExpired ;

    @Column(name = "isCredentialsNonExpired")
    private boolean isCredentialsNonExpired ;

    @Column(name = "isAccountNonLocked")
    private boolean isAccountNonLocked;

    @Column(name = "isEnabled")
    private boolean isEnabled ;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "userRoles",
            joinColumns = {
                    @JoinColumn(name = "userId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "roleId") })
    private Set<Role> roles;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.stream().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));

        });

        return  authorities ;
    }

    @Override
    public String getPassword() {
        return this.Password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
