package com.coviam.shopcarro.authentication.dto;

import javax.validation.constraints.NotNull;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.authentication.dto
 * @project authentication
 */
public class UserDetailsDto {

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String password;


    public UserDetailsDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetailsDto(String email, String firstName, String lastName, String phoneNumber, String address, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
    }

    public UserDetailsDto() {
    }
}
