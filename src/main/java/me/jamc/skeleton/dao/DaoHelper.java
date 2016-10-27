package me.jamc.skeleton.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Jamc on 10/27/16.
 */
@Component
public class DaoHelper {

    @Autowired
    private RequestTimeRepository requestTimeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExceptionRecordRepository exceptionRecordRepository;

    public RequestTimeRepository requestTime() {
        return requestTimeRepository;
    }

    public UserRepository user() {
        return userRepository;
    }

    public ExceptionRecordRepository exceptionRecord() { return exceptionRecordRepository; }
}
