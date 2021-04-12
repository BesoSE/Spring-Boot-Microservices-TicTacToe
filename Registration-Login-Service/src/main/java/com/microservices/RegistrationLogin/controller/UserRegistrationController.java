package com.microservices.RegistrationLogin.controller;

import com.microservices.RegistrationLogin.Config.JwtUtil;
import com.microservices.RegistrationLogin.model.AuthenticationRequest;
import com.microservices.RegistrationLogin.dto.UserDto;
import com.microservices.RegistrationLogin.model.JwtResponse;
import com.microservices.RegistrationLogin.model.User;
import com.microservices.RegistrationLogin.proxy.EmailProxy;
import com.microservices.RegistrationLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserRegistrationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private EmailProxy proxy;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest loginRequest) throws  Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Incorect username or password ",e);

        }
        final UserDetails userDetails=userService.loadUserByUsername(loginRequest.getUsername());
        final String jwt=jwtTokenUtil.TOKEN_PREFIX+jwtTokenUtil.generateToken(userDetails);


        return ResponseEntity.ok(new JwtResponse(jwt,loginRequest.getUsername()));
    }


   @PostMapping("/logout")
    public JwtResponse logout(@RequestBody JwtResponse response) throws Exception {

        return userService.logout(response);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getById(@PathVariable String username) throws Exception {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto){

        User user=null;
        try {
           user= userService.save(userDto);


        }catch (Exception ex){
            System.out.println(ex.getMessage());

        }

       return ResponseEntity.ok(user);
    }
}
