package com.coviam.shopcarro.authentication.repository;

import com.coviam.shopcarro.authentication.model.UserDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.authentication.repository
 * @project authentication
 */
public interface IAuthenticationRepository extends CrudRepository<UserDetails,String> {

    UserDetails findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query("update UserDetails set address=?2 where email=?1")
    void updateByEmail(String email, String address);
}
