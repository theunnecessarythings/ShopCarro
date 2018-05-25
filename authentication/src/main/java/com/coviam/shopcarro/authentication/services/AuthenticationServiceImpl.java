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
            userDetails.setPassword(encryptPassword(userDetailsDto.getPassword()+"shopcarro"));
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

        String encrypted = new String();
        encrypted = encryptPassword(password+"shopcarro");
        //System.out.println(encrypted);
        Optional<UserDetails> userDetails = Optional.ofNullable(iAuthenticationRepository.findByEmailAndPassword(email, encrypted));
        return userDetails.isPresent();

    }

    /**
     * @author sandeepgupta
     *
     * For password encryption in we have used java security
     *
     * appended string "shopcarro" to the password and calculated the hash of that string and later storing it in the database
     * so that it even in the case of server get hacked no one can get the actual password because the hash of the string is
     * getting stored in the database.
     *
     * */

    public String encryptPassword(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            String encryptedString = new String(messageDigest.digest());
            System.out.println(encryptedString);
            return encryptedString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }

}