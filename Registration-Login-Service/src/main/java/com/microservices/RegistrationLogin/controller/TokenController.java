package com.microservices.RegistrationLogin.controller;

import com.microservices.RegistrationLogin.Config.JwtUtil;
import com.microservices.RegistrationLogin.model.JwtResponse;
import com.microservices.RegistrationLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TokenController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/validate")
    public Boolean validateToken(@RequestBody JwtResponse  jwtResponse) throws Exception {
        UserDetails userDetails=userService.loadUserByUsername(jwtResponse.getUsername());
        try {
            if(jwtUtil.validateToken(jwtResponse.getJwt(), userDetails)){
                return true;
            }else{
                throw new Exception("Jwt is not secure");
            }

        } catch (Exception e){
            throw new Exception("Jwt is not secure");
        }



    }
}
