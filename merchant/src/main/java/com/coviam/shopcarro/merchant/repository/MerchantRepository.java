package com.coviam.shopcarro.merchant.repository;

import com.coviam.shopcarro.merchant.model.Merchant;
import com.coviam.shopcarro.merchant.model.Stock;
import com.coviam.shopcarro.merchant.model.key.StockId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.repository
 * @project merchant
 */

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, String> {
    List<Merchant> findByMerchantIdIn(List<String> ids);
}
