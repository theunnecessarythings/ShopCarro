package com.coviam.shopcarro.authentication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.authentication.model
 * @project authentication
 */


@Entity
@Table(name="userdetails")
public class UserDetails {
    @Id
    private String email;

    @Column(name="firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name="phoneNumebr")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "password")
    private String password;

    public UserDetails(String email) {
        this.email = email;
    }

    public UserDetails(String email, String firstName, String lastName, String phoneNumber, String address, String password) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
    }
    public UserDetails(String email, String password) {

        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public UserDetails() {
    }
}
