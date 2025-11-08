package cz.cvut.fel.sit.repository;

import cz.cvut.fel.sit.entity.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {
}
