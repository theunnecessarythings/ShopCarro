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

    /**
     * Whern the user registers for the first time
     *
     *
     * @param userDetailsDto
     * @return
     * @throws CustomException
     */


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
   ResponseEntity<Boolean> signUp(@RequestBody @Valid UserDetailsDto userDetailsDto) throws CustomException {

        System.out.println(userDetailsDto.toString());

        Boolean create = iAuthenticationService.createUser(userDetailsDto);

            return new ResponseEntity<>(create, HttpStatus.CREATED);

    }

    /**
     *
     * To access the user account
     *
     * @param loginDetailsDto
     * @return
     * @throws CustomException
     */

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<Boolean> login(@RequestBody @Valid LoginDetailsDto loginDetailsDto) throws CustomException {
        System.out.println(loginDetailsDto.getEmail());

        Boolean create = iAuthenticationService.loginUser(loginDetailsDto.getEmail(), loginDetailsDto.getPassword());


        return new ResponseEntity<>(create, HttpStatus.CREATED);
        //return "correct";
    }

    /**
     *
     * When there is a need for adding the product ot cart the address must be checked
     *
     * @param email
     * @return
     */

    @RequestMapping(value = "address-check",method = RequestMethod.GET)
    ResponseEntity<Boolean> address(@RequestParam @Valid String email) {
        Boolean create = iAuthenticationService.checkAddress(email);

            return new ResponseEntity<>(create, HttpStatus.CREATED);
        // return "created";
    }

    @RequestMapping(value = "display",method = RequestMethod.GET)
    UserDetailsDto userDetails(@RequestParam String email){
        UserDetailsDto userDetailsDto = iAuthenticationService.display(email);
        return userDetailsDto;
    }

    /**
     * If the user dint mention the address while he is registering , he can update the address while adding to the cart
     *
     *
     * @param email
     * @param address
     * @return
     * @throws CustomException
     */

    @RequestMapping(value = "address-update",method = RequestMethod.GET)
    ResponseEntity<Boolean> addressUpdate(@RequestParam String email,String address) throws CustomException {
        Boolean create = iAuthenticationService.updateAddress(email,address);
        return  new ResponseEntity<>(create,HttpStatus.CREATED);
    }

    }










