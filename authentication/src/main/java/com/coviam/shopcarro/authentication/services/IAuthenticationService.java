package com.coviam.shopcarro.authentication.services;

import com.coviam.shopcarro.authentication.dto.UserDetailsDto;
import com.coviam.shopcarro.authentication.model.UserDetails;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.authentication.services
 * @project authentication
 */
public interface IAuthenticationService {

    public boolean createUser(UserDetailsDto userDetailsDto);
    public boolean loginUser(String email,String password);
    public String encryptPassword(String password);
}
