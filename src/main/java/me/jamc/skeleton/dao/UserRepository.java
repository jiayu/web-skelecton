package me.jamc.skeleton.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import me.jamc.skeleton.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE USER_NAME = :username")
    User findByUsername(@Param("username") String username);
}
