package me.jamc.skeleton.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Jamc on 10/27/16.
 */
@RestController
public class IndexController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

        return "Hello World";
    }

    @RequestMapping(value = "/login/github", method = RequestMethod.GET)
    public String github() {

        return "This is for github login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {

        return "This is home";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {

        return "This is error page";
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
