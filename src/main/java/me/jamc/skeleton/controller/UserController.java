package me.jamc.skeleton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.jamc.skeleton.model.User;
import me.jamc.skeleton.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/api/user/{firstName}/{lastName}", method = RequestMethod.PUT)
    public boolean addUser(@PathVariable String firstName, @PathVariable String lastName) {
        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            return false;
        }
        return service.addUser(firstName, lastName);
    }

    @RequestMapping(value = "/api/user/{id}/{firstName}/{lastName}", method=RequestMethod.POST) 
    public boolean updateUser(@PathVariable int id, @PathVariable String firstName,
            @PathVariable String lastName) {
        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            return false;
        } 
        return service.updateUser(id, firstName, lastName);
    }

    @RequestMapping(value= "/api/user/{id}", method=RequestMethod.GET)
    public User getUser(@PathVariable int id) {

        return service.getUser(id);
    }

    @RequestMapping(value = "/api/user/{id}", method=RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable int id) {

        return service.deleteUser(id);
    }

    @RequestMapping(value = "/api/user/all", method=RequestMethod.GET)
    public List<User> getUsers() {

        return service.getAllUsers();
    }
    
}
