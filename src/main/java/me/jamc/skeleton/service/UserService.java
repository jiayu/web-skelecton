package me.jamc.skeleton.service;

import java.util.List;

import me.jamc.skeleton.model.User;

public interface UserService {

    public boolean addUser(String firstName, String lastName);
    public boolean updateUser(int id, String firstName, String lastName);
    public User getUser(int id);
    public boolean deleteUser(int id);
    public List<User> getAllUsers();

}
