package me.jamc.skeleton.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jamc.skeleton.model.User;
import me.jamc.skeleton.dao.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public boolean addUser(String firstName, String lastName) {
        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        userRepo.save(u);
        return true;
    }

    @Override
    public boolean updateUser(int id, String firstName, String lastName) {
        User u = userRepo.findOne(id);
        if (null != u) {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            userRepo.save(u);
            return true;
        }
        return false;
    }

    @Override
    public User getUser(int id) {
        return userRepo.findOne(id);
    }

    @Override
    public boolean deleteUser(int id) {
        User u = userRepo.findOne(id);
        if (null != u) {
            userRepo.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new LinkedList<User>();
        userRepo.findAll().forEach( u -> list.add(u));
        return list;
    }

}
