package com.coviam.shopcarro.authentication.dto;

import javax.validation.constraints.NotNull;

public class LoginDetailsDto {

    private String email;

    private String password;

    public LoginDetailsDto(String email, String password) {
        this.email = email;
        this.password = password;
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

    public LoginDetailsDto() {
    }
}
