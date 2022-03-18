package com.JWT.jwtAuthRoleBased.repo;

import com.JWT.jwtAuthRoleBased.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser  findByuserName(String name );
}
