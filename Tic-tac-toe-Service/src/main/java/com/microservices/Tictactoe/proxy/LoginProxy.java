package com.microservices.Tictactoe.proxy;

import com.microservices.Tictactoe.dto.LoginRequest;
import com.microservices.Tictactoe.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="registration-login")
public interface LoginProxy {
    @PostMapping("/api/authenticate")
    public LoginResponse retriveDataForEmail(LoginRequest loginRequest);

    @PostMapping("/api/validate")
    public Boolean retriveValidateToken(LoginResponse loginResponse);
}
