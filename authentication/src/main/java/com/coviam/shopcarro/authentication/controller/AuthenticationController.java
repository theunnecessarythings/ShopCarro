package com.coviam.shopcarro.authentication.controller;

import com.coviam.shopcarro.authentication.dto.LoginDetailsDto;
import com.coviam.shopcarro.authentication.dto.UserDetailsDto;

import com.coviam.shopcarro.authentication.services.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.MessageDigest;


/**
 * @author Sruthi
 * @package com.coviam.shopcarro.authentication.controller
 * @project authentication
 */

/**
 *
 * @author: Sandeep Gupta
 * Password Encryption
 *
 * */

@RestController
@RequestMapping("userAuthentication")
public class AuthenticationController {
    @Autowired
    private IAuthenticationService iAuthenticationService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
   ResponseEntity<Boolean> signUp(@RequestBody @Valid UserDetailsDto userDetailsDto) {
        System.out.println(userDetailsDto.toString());

        boolean create = iAuthenticationService.createUser(userDetailsDto);
        if(!create) {
          return new ResponseEntity<>(create, HttpStatus.CREATED);
          //  return "failed";
            }
        else
            return new ResponseEntity<>(create, HttpStatus.CREATED);
       // return "created";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<Boolean> login(@RequestBody @Valid LoginDetailsDto loginDetailsDto) {
        System.out.println(loginDetailsDto.getEmail());
        if(loginDetailsDto.getEmail().isEmpty() || loginDetailsDto.getPassword().isEmpty()){
            return new ResponseEntity<> (false,HttpStatus.CREATED);

        boolean create = iAuthenticationService.loginUser(loginDetailsDto.getEmail(),loginDetailsDto.getPassword());
        if(!create){
            return new ResponseEntity<>(create, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(create, HttpStatus.CREATED);
            //return "correct";
        }







}
