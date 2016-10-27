package me.jamc.skeleton.handler;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.jamc.skeleton.dao.DaoHelper;
import me.jamc.skeleton.exception.ValidationException;
import me.jamc.skeleton.model.ExceptionRecord;
import me.jamc.skeleton.security.Authorization;

/**
 * Created by Jamc on 10/26/16.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private Authorization sign;

    @Autowired
    private DaoHelper helper;

    @ExceptionHandler(Exception.class)
    public void defaultExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        LOG.error("Error {}!!", e.getMessage());

        String method = request.getMethod();
        String uri = request.getRequestURI();
        ExceptionRecord r = new ExceptionRecord();
        r.setMethod(method);
        r.setUri(uri);
        r.setUpdateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        r.setExceptionMessage(e.getMessage());
        r.setExceptionName(e.getClass().getName());
        helper.exceptionRecord().save(r);

        throw e;
    }

    @ExceptionHandler(ValidationException.class)
    public void validationExceptionHandler(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String params = request.getQueryString();
        String secretKey = request.getHeader("accessKey");
        String signature = request.getHeader("signature");
        long timestamp = Long.parseLong(request.getHeader("timestamp"));
        LOG.error("Validation exception expected signature is {} while given one is {}",
                signature, sign.sign(uri,method, params, secretKey, timestamp));

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
