package com.coviam.shopcarro.merchant.repository;

import com.coviam.shopcarro.merchant.model.Merchant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.repository
 * @project merchant
 */

@Repository
public interface IMerchantRepository extends CrudRepository<Merchant, String> {
}
