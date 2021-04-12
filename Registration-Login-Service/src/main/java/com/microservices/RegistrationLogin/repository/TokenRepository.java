package com.microservices.RegistrationLogin.repository;

import com.microservices.RegistrationLogin.model.JwtResponse;
import com.microservices.RegistrationLogin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TokenRepository extends JpaRepository<JwtResponse, String> {
    JwtResponse findByJwt(String jwt);
}

