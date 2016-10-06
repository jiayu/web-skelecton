package me.jamc.skeleton.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.jamc.skeleton.model.User;

@RestController
public class UserController {

    private static Map<Integer, User> userDB = new HashMap<Integer, User>();
    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping(value = "/user/{firstName}/{lastName}", method = RequestMethod.PUT)
    public boolean addUser(@PathVariable String firstName, @PathVariable String lastName) {
        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setId(counter.incrementAndGet());
        userDB.put(u.getId(), u);
        return true;
    }

    @RequestMapping(value = "/user/{id}/{firstName}/{lastName}", method=RequestMethod.POST) 
    public boolean updateUser(@PathVariable int id, @PathVariable String firstName,
            @PathVariable String lastName) {
        if (userDB.containsKey(id)) {
            User u = userDB.get(id);
            u.setFirstName(firstName);
            u.setLastName(lastName);
            return true;
        }
        return false;
    }

    @RequestMapping(value= "/user/{id}", method=RequestMethod.GET)
    public User getUser(@PathVariable int id) {
        User u = userDB.get(id);
        return u;
    }

    @RequestMapping(value = "/user/{id}", method=RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable int id) {
        if (userDB.containsKey(id)) {
            userDB.remove(id);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/user/all", method=RequestMethod.GET)
    public List<User> getUsers() {
        List<User> list = new LinkedList<User>();
        userDB.keySet().forEach( k -> {list.add(userDB.get(k));});
        return list;
    }
    
}
