package com.microservices.RegistrationLogin.service;

import com.microservices.RegistrationLogin.dto.UserDto;
import com.microservices.RegistrationLogin.model.JwtResponse;
import com.microservices.RegistrationLogin.model.Role;
import com.microservices.RegistrationLogin.model.User;
import com.microservices.RegistrationLogin.repository.RoleRepository;

import com.microservices.RegistrationLogin.repository.TokenRepository;
import com.microservices.RegistrationLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  TokenRepository tokenRepository;



    @Override
    public User save(UserDto userDto) throws Exception {
       User user=new User();
       if(userRepository.findByEmail(userDto.getEmail())==null) {
           user.setEmail(userDto.getEmail());
       }else{
           throw new Exception("Email is used");
       }

       if(userRepository.findByUserName(userDto.getUserName())==null){
           user.setUserName(userDto.getUserName());
       }else{
           throw new Exception("Username is used");
       }

       user.setPassword(passwordEncoder.encode(userDto.getPassword()));
       Role role;
       if(roleRepository.findByRole("ROLE_USER")!=null){
           role=roleRepository.findByRole("ROLE_USER");
       }else{
           role=new Role();
           role.setRole("ROLE_USER");
           roleRepository.save(role);
       }

       user.setRoles(new HashSet<Role>(Arrays.asList(role)));
       user.setStatus(true);



       return userRepository.save(user);

    }

    @Override
    public User getByUsername(String username) throws Exception {
        User user=null;
        try {
           user=userRepository.findByUserName(username);
        }catch(Exception e){
            throw new Exception("There is no user with username "+username);
        }
        return user;
    }

   @Override
    public JwtResponse logout(JwtResponse response) throws Exception {
        if(response!=null){
            try{
                User user=getByUsername(response.getUsername());
                response.setUser(user);
                response.setJwt(response.getJwt().substring(7));
                tokenRepository.save(response);
            }catch (Exception e){
                throw new Exception("Cann't save token");
            }
        }else{
            throw new Exception("No token");
        }
        JwtResponse r=new JwtResponse(response.getJwt(),response.getUsername());
        return  r;
    }

    @Override
    public Boolean getJwtByJwt(String jwt)  {
        Optional<JwtResponse> optional=tokenRepository.findById(jwt);
        if(optional.isPresent()) return true;
        else{
            return false;


        }


    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userRepository.findByUserName(s);

        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role->new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
    }
}
