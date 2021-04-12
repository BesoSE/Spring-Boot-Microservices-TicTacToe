package com.microservices.RegistrationLogin.Config;

import com.microservices.RegistrationLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private  UserService userService;

    @Autowired
    private  JwtUtil jwtUtil;





    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token= httpServletRequest.getHeader("Authorization");
        String username=null;
        String jwt=null;

        if(token!=null && token.startsWith("Bearer ")){

            jwt=token.substring(7); //jwt is token without Bearer
            username=jwtUtil.extractUsername(jwt);
        }



                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { //if there is on authenitcate user it will put it into SecurityContextHolder

                    UserDetails userDetails = userService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }

            }


        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
