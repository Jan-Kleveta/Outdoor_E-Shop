package cz.cvut.fel.sit.repository;

import cz.cvut.fel.sit.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);
}
