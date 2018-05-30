package com.coviam.shopcarro.authentication.services;

import com.coviam.shopcarro.authentication.CustomException;
import com.coviam.shopcarro.authentication.dto.UserDetailsDto;
import com.coviam.shopcarro.authentication.model.UserDetails;
import com.coviam.shopcarro.authentication.repository.IAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.security.MessageDigest;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final Integer maxlength=250;
    private final Integer namelength=50;

    @Autowired
    private IAuthenticationRepository iAuthenticationRepository;

    @Override
    public boolean createUser(UserDetailsDto userDetailsDto) throws CustomException {
        validateUser(userDetailsDto);
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
            System.out.println(userDetailsDto.getPassword());
            userDetails.setPassword(encryptPassword(userDetailsDto.getPassword()+"shopcarro"));
            iAuthenticationRepository.save(userDetails);
            return true;
        }
    }




    @Override
    public boolean loginUser(String email, String password) throws CustomException {
        /**
         *  Checking whether the user is a registered user or not
         *
         * */

        validateLoginUser(email,password);

        String encrypted = new String();
        encrypted = encryptPassword(password+"shopcarro");
        //System.out.println(encrypted);
        Optional<UserDetails> userDetails = Optional.ofNullable(iAuthenticationRepository.findByEmailAndPassword(email, encrypted));
        return userDetails.isPresent();

    }


    /**
     *
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


    @Override
    public boolean checkAddress(String email) {
        Optional<UserDetails> userDetails1 = iAuthenticationRepository.findById(email);
        System.out.println(userDetails1.get().getEmail());
        if (!userDetails1.isPresent()) {
            return false;

        }
        System.out.println(userDetails1.get().getAddress());

        if(userDetails1.get().getAddress()!=null){
            return true;
        }
        return false;

    }

    /**
     *
     * Validating according to the constraints
     *
     *
     * @param userDetailsDto
     * @return
     * @throws CustomException
     */

    private boolean validateUser(UserDetailsDto userDetailsDto) throws CustomException {
        if(userDetailsDto.getEmail()==null || userDetailsDto.getLastName()==null || userDetailsDto.getFirstName()==null || userDetailsDto.getPassword()==null ){
            throw new  CustomException("false");
        }
        if(userDetailsDto.getEmail().length() > maxlength|| userDetailsDto.getFirstName().length()>namelength || userDetailsDto.getLastName().length()>namelength ){
            throw new CustomException("false");
        }
        return true;
    }

    private boolean validateLoginUser(String email,String password)throws CustomException{
        if(email==null|| password==null){
            throw new CustomException("false");
        }
        return true;
    }

    /**
     *
     *
     * Updating the address of the user
     *
     *
     * @param email
     * @param address
     * @return
     * @throws CustomException
     */

    @Transactional
    @Modifying
    public boolean updateAddress(String email, String address) throws CustomException {
        if (!validateAddress(email, address)) {
            return false;
        }
        if (String.valueOf(iAuthenticationRepository.findById(email)).equals("Optional.empty")) {
            throw new CustomException("false");
        } else {
            iAuthenticationRepository.updateByEmail(email, address);
            return true;
        }

    }

    @Override
    public UserDetailsDto display(String email) {
        Optional<UserDetails> userDetails1 = iAuthenticationRepository.findById(email);
        if(!userDetails1.isPresent())
            return null;
        return new UserDetailsDto(userDetails1.get().getEmail(),userDetails1.get().getFirstName(),userDetails1.get().getLastName(),userDetails1.get().getPhoneNumber(),userDetails1.get().getAddress(),null);

    }

    private boolean validateAddress(String email,String address)throws CustomException{
        if(email==null|| address==null){
            throw new CustomException("false");
        }
        return true;
    }

}