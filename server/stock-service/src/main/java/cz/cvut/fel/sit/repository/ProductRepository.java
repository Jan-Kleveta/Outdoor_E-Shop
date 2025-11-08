package cz.cvut.fel.sit.repository;

import cz.cvut.fel.sit.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    public boolean existsById(Long id);
}
