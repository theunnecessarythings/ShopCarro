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

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.authentication.controller
 * @project authentication
 */
@RestController
@RequestMapping("userAuthentication")
public class AuthenticationController {
    @Autowired
    private IAuthenticationService iAuthenticationService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
   ResponseEntity<Boolean> signUp(@RequestBody @Valid UserDetailsDto userDetailsDto) {

        boolean create = iAuthenticationService.createUser(userDetailsDto);
        if(!create) {
          return new ResponseEntity<>(create, HttpStatus.NOT_ACCEPTABLE);
          //  return "failed";
            }
        else
            return new ResponseEntity<>(create, HttpStatus.CREATED);
       // return "created";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<Boolean> login(@RequestBody @Valid LoginDetailsDto loginDetailsDto) {
        if(loginDetailsDto.getEmail().isEmpty() || loginDetailsDto.getPassword().isEmpty()){
            return new ResponseEntity<> (false,HttpStatus.NOT_ACCEPTABLE);
        }

        boolean create = iAuthenticationService.loginUser(loginDetailsDto.getEmail(),loginDetailsDto.getPassword());

        if(!create){

            return new ResponseEntity<>(create, HttpStatus.NOT_ACCEPTABLE);

        }


            return new ResponseEntity<>(create, HttpStatus.CREATED);
            //return "correct";



        }





}
