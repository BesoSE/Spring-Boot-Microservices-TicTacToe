package com.microservices.RegistrationLogin.service;

import com.microservices.RegistrationLogin.dto.UserDto;
import com.microservices.RegistrationLogin.model.JwtResponse;
import com.microservices.RegistrationLogin.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserDto userDto) throws Exception;
    User getByUsername(String username) throws Exception;
    JwtResponse logout(JwtResponse response) throws Exception;
    Boolean getJwtByJwt(String jwt);
    Boolean checkUsername(String username);
    Boolean checkEmail(String email);
}
