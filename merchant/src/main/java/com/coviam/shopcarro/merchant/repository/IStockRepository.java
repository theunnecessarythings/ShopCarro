package com.coviam.shopcarro.merchant.repository;

import com.coviam.shopcarro.merchant.model.Stock;
import com.coviam.shopcarro.merchant.model.key.StockId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sreerajr
 * @package com.coviam.shopcarro.merchant.repository
 * @project merchant
 */

@Repository
public interface IStockRepository extends CrudRepository<Stock, StockId> {
}
