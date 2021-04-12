package com.microservices.Tictactoe.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.microservices.Tictactoe.controller.GameController;
import com.microservices.Tictactoe.dto.LoginResponse;
import com.microservices.Tictactoe.proxy.LoginProxy;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private  LoginProxy proxy;


    public Boolean validateToken(LoginResponse loginResponse){

        return proxy.retriveValidateToken(loginResponse);
    }


    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token= httpServletRequest.getHeader("Authorization");
        String username=null;
        String jwt=null;

        if(token!=null && token.startsWith("Bearer ")) {

            jwt = token.substring(7); //jwt is token without Bearer
            username = extractUsername(jwt);
        }

            LoginResponse loginResponse=new LoginResponse(jwt,username);
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            if (validateToken(loginResponse) == true) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }


        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
