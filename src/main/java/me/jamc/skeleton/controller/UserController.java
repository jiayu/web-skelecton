package me.jamc.skeleton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import me.jamc.skeleton.model.User;
import me.jamc.skeleton.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.PUT)
    @ApiOperation(value = "Add a new user", 
        notes = "use for adding a new user, firstname and lastname could not be empty")
    public boolean addUser(@PathVariable String firstName, @PathVariable String lastName) {
        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            return false;
        }
        return service.addUser(firstName, lastName);
    }

    @RequestMapping(value = "/{id}/{firstName}/{lastName}", method=RequestMethod.POST)
    @ApiOperation(value = "update an existing user", notes = "use to udpate an existing user")
    public boolean updateUser(@PathVariable int id, @PathVariable String firstName,
            @PathVariable String lastName) {
        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            return false;
        } 
        return service.updateUser(id, firstName, lastName);
    }

    @RequestMapping(value= "/{id}", method=RequestMethod.GET)
    @ApiOperation(value = "get back a user by id", notes = "Use")
    public User getUser(@PathVariable int id) {

        return service.getUser(id);
    }

    @RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
    @ApiOperation(value = "remove a user by id", notes = "Use to remove a user")
    public boolean deleteUser(@PathVariable int id) {

        return service.deleteUser(id);
    }

    @RequestMapping(value = "/all", method=RequestMethod.GET)
    @ApiOperation(value = "get back all the exisiting users")
    public List<User> getUsers() {

        return service.getAllUsers();
    }
    
}
