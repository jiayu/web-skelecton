package me.jamc.skeleton.security;

import javax.servlet.ServletRequest;

public interface Authenticator {

    public boolean auth(ServletRequest request);
}
