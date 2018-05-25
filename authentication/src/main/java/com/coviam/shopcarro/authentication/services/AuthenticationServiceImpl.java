package com.coviam.shopcarro.authentication.services;

import com.coviam.shopcarro.authentication.dto.UserDetailsDto;
import com.coviam.shopcarro.authentication.model.UserDetails;
import com.coviam.shopcarro.authentication.repository.IAuthenticationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.security.MessageDigest;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private IAuthenticationRepository iAuthenticationRepository;

    @Override
    public boolean createUser(UserDetailsDto userDetailsDto) {
        UserDetails userDetails = new UserDetails(userDetailsDto.getEmail(), userDetailsDto.getFirstName(), userDetailsDto.getLastName(), userDetailsDto.getPhoneNumber(), userDetailsDto.getAddress(), userDetailsDto.getPassword());
//        BeanUtils.copyProperties();
        Optional<UserDetails> userDetails1 = iAuthenticationRepository.findById(userDetailsDto.getEmail());
        /**
         *  Checking whether the user already exist
         *
         * */
        if (userDetails1.isPresent()) {
            return false;
        } else {
            iAuthenticationRepository.save(userDetails);
            return true;
        }
    }

    @Override
    public boolean loginUser(String email, String password)  {
        /**
         *  Checking whether the user is a registered user or not
         *
         * */
        Optional<UserDetails> userDetails = Optional.ofNullable(iAuthenticationRepository.findByEmailAndPassword(email, password));
        return userDetails.isPresent();
    }

}