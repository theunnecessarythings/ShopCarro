package com.coviam.shopcarro.authentication.repository;

import com.coviam.shopcarro.authentication.model.UserDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.authentication.repository
 * @project authentication
 */
public interface IAuthenticationRepository extends CrudRepository<UserDetails,String> {
/*
    @Query("Select * from UserDetails where email=:email and password=:password")
    public UserDetails findByIdAndDetails(@Param("email") String email,@Param("password") String password);
    */
    UserDetails findByEmailAndPassword(String email, String password);
}
