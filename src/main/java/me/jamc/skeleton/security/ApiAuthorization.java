package me.jamc.skeleton.security;

import org.assertj.core.util.Preconditions;
import org.assertj.core.util.Strings;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by Jamc on 10/26/16.
 * P
 */
@Component
public class ApiAuthorization implements Authorization {
    private static final Logger LOG = LoggerFactory.getLogger(ApiAuthorization.class);


    @Override
    public boolean verify(String uri, String method, String params, long timestamp, String secretKey,
                          String signature) {
        Preconditions.checkNotNullOrEmpty(uri, "uri is null or empty when trying to sign");
        Preconditions.checkNotNullOrEmpty(method, "method is null or empty when trying to sign");
        Preconditions.checkNotNullOrEmpty(secretKey, "secretKey is null or empty when trying to sign");
        Preconditions.checkNotNullOrEmpty(signature, "signature is null or empty when trying to sign");
        Preconditions.checkNotNull(timestamp, "timestamp is null when tyring to sign");


        return isWithinValidRequestTime(timestamp) &&
                signature.equals(sign(uri, method, params, secretKey, timestamp));
    }

    private boolean isWithinValidRequestTime(long timestamp) {

        DateTime currentTime = new DateTime();
        DateTime requestTime = new DateTime(timestamp);

        Seconds seconds = Seconds.secondsBetween(requestTime, currentTime);

        LOG.info("Current time {} vs request time {}, seconds in between is {}", currentTime,
                requestTime, seconds.getSeconds());

        return Math.abs(seconds.getSeconds()) < 5 * 60;
    }

    @Override
    public String sign(String uri, String method, String params, String secretKey,
                       long timestamp) {
        Preconditions.checkNotNullOrEmpty(uri, "uri is null or empty when trying to sign");
        Preconditions.checkNotNullOrEmpty(method, "method is null or empty when trying to sign");
        Preconditions.checkNotNullOrEmpty(secretKey, "secretKey is null or empty when trying to sign");
        Preconditions.checkNotNull(timestamp, "timestamp is null when tyring to sign");

        String strToSign = Strings.concat(uri, method, params, timestamp, secretKey);
        String signature  = SHA1(strToSign);
        return signature;
    }

    private String SHA1(String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            md.update(input.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }

        byte byteData[] = md.digest();
        return DatatypeConverter.printHexBinary(byteData).toLowerCase();
    }
}
