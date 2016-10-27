package me.jamc.skeleton.dao;

import org.springframework.data.repository.CrudRepository;

import me.jamc.skeleton.model.RequestTime;

/**
 * Created by Jamc on 10/27/16.
 */
public interface RequestTimeRepository extends CrudRepository<RequestTime, Long> {
}
