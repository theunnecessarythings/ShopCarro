package com.coviam.shopcarro.authentication.controller;

import com.coviam.shopcarro.authentication.CustomException;
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
   ResponseEntity<Boolean> signUp(@RequestBody @Valid UserDetailsDto userDetailsDto) throws CustomException {

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
    ResponseEntity<Boolean> login(@RequestBody @Valid LoginDetailsDto loginDetailsDto) throws CustomException {
        System.out.println(loginDetailsDto.getEmail());

        boolean create = iAuthenticationService.loginUser(loginDetailsDto.getEmail(),loginDetailsDto.getPassword());
        if(!create){
            return new ResponseEntity<>(create, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(create, HttpStatus.CREATED);
            //return "correct";
        }

    @RequestMapping(value = "address-check",method = RequestMethod.GET)
    ResponseEntity<Boolean> address(@RequestParam @Valid String email) {
        boolean create = iAuthenticationService.checkAddress(email);
        if(!create) {
            return new ResponseEntity<>(create, HttpStatus.CREATED);
            //  return "failed";
        }
        else
            return new ResponseEntity<>(create, HttpStatus.CREATED);
        // return "created";
    }

    @RequestMapping(value = "address-update",method = RequestMethod.GET)
    ResponseEntity<Boolean> addressUpdate(@RequestParam String email,String address) throws CustomException {
        boolean create = iAuthenticationService.updateAddress(email,address);
        return  new ResponseEntity<>(create,HttpStatus.CREATED);
    }

    }








}

