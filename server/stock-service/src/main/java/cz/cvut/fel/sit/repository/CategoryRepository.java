package cz.cvut.fel.sit.repository;

import cz.cvut.fel.sit.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
