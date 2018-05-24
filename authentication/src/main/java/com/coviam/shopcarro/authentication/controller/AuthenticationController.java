package com.coviam.shopcarro.authentication.controller;

import com.coviam.shopcarro.authentication.dto.UserDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.authentication.controller
 * @project authentication
 */
public class AuthenticationController {

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    ResponseEntity<Boolean> signUp(@RequestBody UserDetailsDto userDetailsDto) {

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    ResponseEntity<Boolean> login(@RequestParam String username, @RequestParam String password) {

    }


}
