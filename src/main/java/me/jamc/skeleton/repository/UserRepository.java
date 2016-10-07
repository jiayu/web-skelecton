package me.jamc.skeleton.repository;

import org.springframework.data.repository.CrudRepository;

import me.jamc.skeleton.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
