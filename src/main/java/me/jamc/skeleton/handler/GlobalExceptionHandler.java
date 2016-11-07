package me.jamc.skeleton.handler;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import me.jamc.skeleton.controller.response.ErrorResponse;
import me.jamc.skeleton.dao.DaoHelper;
import me.jamc.skeleton.model.ExceptionRecord;

/**
 * Created by Jamc on 10/26/16.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private DaoHelper helper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(HttpServletRequest request, Exception e) {
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

        ErrorResponse er = new ErrorResponse();
        er.setSuccess(false);
        er.setException(e.getClass().getName());
        er.setErrorMsg(e.getMessage());
        return ResponseEntity.ok().body(er);
    }

}
