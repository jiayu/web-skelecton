package me.jamc.skeleton.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import me.jamc.skeleton.security.Authenticator;

@WebFilter(urlPatterns = "/api/user/*")
public class SecurityFilter implements Filter {
    
    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private Authenticator authenticator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest)request;
        if(authenticator.auth(request)) {
            chain.doFilter(request, response);
            LOG.info("Authentication passed when calling url {}", r.getRequestURI());
        } else {
            LOG.error("Authentication failed when calling url {}", r.getRequestURI());
            HttpServletResponse rs = (HttpServletResponse)response;
            rs.getWriter().print("Authentication Error");
            rs.getWriter().flush();
            return;
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
