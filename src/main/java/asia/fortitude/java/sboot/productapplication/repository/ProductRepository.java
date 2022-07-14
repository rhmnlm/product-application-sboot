package asia.fortitude.java.sboot.productapplication.repository;

import asia.fortitude.java.sboot.productapplication.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,Integer> {

    Page<Product> findByCode(String code, Pageable pageable);
}
