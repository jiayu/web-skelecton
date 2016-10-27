package me.jamc.skeleton.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.jamc.skeleton.exception.ValidationException;
import me.jamc.skeleton.security.Authorization;

/**
 * Created by Jamc on 10/26/16.
 */
@Component
public class ValidationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationInterceptor.class);

    @Autowired
    private Authorization sign;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String params = request.getQueryString();
        String secretKey = request.getHeader("accessKey");
        String signature = request.getHeader("signature");
        long timestamp = Long.parseLong(request.getHeader("timestamp"));
        if (sign.verify(uri, method, params, timestamp, secretKey, signature)) {
            return true;
        } else {
            throw new ValidationException("Failed on signature validation");
        }
    }
}
