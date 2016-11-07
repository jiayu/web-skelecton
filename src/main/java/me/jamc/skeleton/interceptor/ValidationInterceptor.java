package me.jamc.skeleton.interceptor;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.google.common.io.CharStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.jamc.skeleton.exception.ValidationException;

/**
 * Created by Jamc on 10/26/16.
 */
@Component
public class ValidationInterceptor extends HandlerInterceptorAdapter implements InitializingBean{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.skeleton.validation.timeAllow}")
    private int timeAllow;

    @Autowired
    private MessageSource ms;

    private String msgTimeExpired;
    private String msgSignFailed;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ValidationException, IOException {

        String nonce = request.getHeader("nonce");
        String signature = request.getHeader("signature");
        String token = request.getHeader("token");
        long timestamp = Long.parseLong(request.getHeader("timestamp"));
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String params = request.getQueryString();
        String body = CharStreams.toString(request.getReader()).toString();

        //Verification
        isValidNonce(nonce);
        isWithinValidRequestTime(timestamp);
        isValidSignature(signature, uri, method, params, nonce, timestamp, token, body);

        return true;
    }

    private void isValidNonce(String nonce) throws ValidationException{
        //TODO implement NONCE check
    }

    private void isWithinValidRequestTime(long timestamp) throws ValidationException{
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime requestTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+08:00"));
        Duration duration = Duration.between(requestTime, now);

        log.info("Current time {} vs request time {}, seconds in between is {} mins", now,
                requestTime, duration.toMinutes());
        if (Math.abs(duration.toMinutes()) > timeAllow ) {
            throw new ValidationException(msgTimeExpired);
        }
    }

    private void isValidSignature(String signature, String uri, String method, String params, String nonce,
                                  long timestamp, String token, String body) throws ValidationException{
        String actualSignature = sign(uri, method, params, nonce, timestamp, token, body);
        if(!actualSignature.equals(signature)) {
            throw new ValidationException(msgSignFailed);
        }
    }

    private String sign(String uri, String method, String params, String nonce,
                        long timestamp, String token, String body) {
        Preconditions.checkNotNull(uri, msgNullPointer("URI"));
        Preconditions.checkNotNull(method, msgNullPointer("METHOD"));
        Preconditions.checkNotNull(nonce, msgNullPointer("NONCE"));
        Preconditions.checkNotNull(timestamp, msgNullPointer("TIMESTAMP"));

        List<String> l = ImmutableList.of(body, method, nonce, params, String.valueOf(timestamp), token, uri);
        String strToSign = Joiner.on(":").join(l);
        log.debug("String to sign is {}", strToSign);

        String signature  = Hashing.sha1().hashString(strToSign, Charsets.UTF_8).toString();
        return signature;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        msgTimeExpired = ms.getMessage("error.validation.time.expired", null, Locale.getDefault());
        msgSignFailed = ms.getMessage("error.validation.signature.failed", null, Locale.getDefault());
    }

    private String msgNullPointer(String field) {
        return ms.getMessage("error.nullpointer.check", new String[]{field, "Sign"}, Locale.getDefault());
    }
}
