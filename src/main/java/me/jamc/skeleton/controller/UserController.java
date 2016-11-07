package me.jamc.skeleton.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import me.jamc.skeleton.controller.request.BasicRequest;
import me.jamc.skeleton.controller.response.AppResponse;
import me.jamc.skeleton.model.User;
import me.jamc.skeleton.service.UserService;

@RestController
@RequestMapping(value = "/app/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService service;

    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.POST)
    @ApiOperation(value = "Add a new user", 
        notes = "use for adding a new user, firstname and lastname could not be empty")
    public AppResponse addUser(@PathVariable String firstName, @PathVariable String lastName,
                               @RequestBody BasicRequest request) {
        log.info("The request body is {}", request);
        AppResponse r = new AppResponse();
        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
            r.setSuccess(false);
            return r;
        }

        boolean f = service.addUser(firstName, lastName);
        r.setSuccess(f);

        return r;
    }

    @RequestMapping(value = "/{id}/{firstName}/{lastName}", method=RequestMethod.PUT)
    @ApiOperation(value = "update an existing user", notes = "use to update an existing user")
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
    @ApiOperation(value = "get back all the existing users")
    public List<User> getUsers() {

        return service.getAllUsers();
    }
    
}
