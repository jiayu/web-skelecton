package me.jamc.skeleton.dao;

import org.springframework.data.repository.CrudRepository;

import me.jamc.skeleton.model.ExceptionRecord;

/**
 * Created by Jamc on 10/27/16.
 */
public interface ExceptionRecordRepository extends CrudRepository<ExceptionRecord, Long> {
}
