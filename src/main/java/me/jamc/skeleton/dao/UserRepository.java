package me.jamc.skeleton.dao;

import org.springframework.data.repository.CrudRepository;

import me.jamc.skeleton.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
