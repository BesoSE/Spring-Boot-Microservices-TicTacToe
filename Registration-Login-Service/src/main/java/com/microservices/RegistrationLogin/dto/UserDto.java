package com.microservices.RegistrationLogin.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {
    @NotNull
    @NotEmpty(message = "Please enter your username")
    private String userName;
    @NotNull
    @NotEmpty(message = "Please enter your email")
    private String email;
    @NotNull
    @NotEmpty(message = "Please enter password")
    @Size(min = 5, max = 20, message ="Please enter minimum 5 char and maximum 20 char")
    private String password;
    @NotNull
    @NotEmpty(message = "Please enter repeat password")
    private String repeatPwd;

    public UserDto() {
    }

    public boolean checkPassword(){
        if(this.password.equals(repeatPwd)){
            return true;
        }
        return false;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPwd() {
        return repeatPwd;
    }

    public void setRepeatPwd(String repeatPwd) {
        this.repeatPwd = repeatPwd;
    }
}
