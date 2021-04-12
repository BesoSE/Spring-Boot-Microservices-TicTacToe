package com.microservices.RegistrationLogin.repository;

import com.microservices.RegistrationLogin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByUserName(String userName);
}
