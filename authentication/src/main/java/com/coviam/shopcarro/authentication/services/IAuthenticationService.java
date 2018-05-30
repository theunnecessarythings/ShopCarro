package com.coviam.shopcarro.authentication.services;

import com.coviam.shopcarro.authentication.CustomException;
import com.coviam.shopcarro.authentication.dto.UserDetailsDto;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.authentication.services
 * @project authentication
 */
public interface IAuthenticationService {

    public boolean createUser(UserDetailsDto userDetailsDto) throws CustomException;
    public boolean loginUser(String email,String password) throws CustomException;
    public String encryptPassword(String password);

     public  boolean checkAddress(String email);
    public boolean updateAddress(String email, String address) throws CustomException;
    public UserDetailsDto display(String email);
}
