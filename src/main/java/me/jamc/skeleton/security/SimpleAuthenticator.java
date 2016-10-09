package me.jamc.skeleton.security;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value = "Simple")
public class SimpleAuthenticator implements Authenticator {

    @Override
    public boolean auth(ServletRequest request) {
        HttpServletRequest r = (HttpServletRequest) request;
        String authStr = r.getHeader("Authorization");
        return true;
//        return !StringUtils.isEmpty(authStr);
    }

}
