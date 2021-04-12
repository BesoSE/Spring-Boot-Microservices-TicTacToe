package com.microservices.RegistrationLogin.model;

import javax.persistence.*;

@Entity
@Table(name = "Token")
public class JwtResponse {
  /*  @Id
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "id",referencedColumnName = "user_id")
    private User user;*/
    @Id
    private String jwt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @Transient
    private String username;

    public JwtResponse() {
    }
    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    public JwtResponse(String jwt, String username) {
        this.jwt = jwt;
        this.username = username;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
