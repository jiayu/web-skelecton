package me.jamc.skeleton.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

import me.jamc.skeleton.controller.response.ErrorResponse;
import me.jamc.skeleton.exception.ValidationException;

/**
 * Created by Jamc on 11/7/16.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleAppValidationError(ValidationException e)
            throws IOException {
        ErrorResponse r = new ErrorResponse();
        r.setSuccess(false);
        r.setException(e.getClass().getName());
        r.setErrorMsg(e.getMessage());
        return ResponseEntity.ok().body(r);
    }
}
