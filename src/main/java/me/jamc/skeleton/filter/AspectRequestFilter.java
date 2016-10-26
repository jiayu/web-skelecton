package me.jamc.skeleton.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jamc on 10/26/16.
 *
 * With the filter provided by Spring MVC, we can't easily get the time with a certain request endpoint.
 * Thus, I added the following request filter to achieve such goal.
 * Besides, the authentication could be finished here too.
 */
@Aspect
@Configuration
public class AspectRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AspectRequestFilter.class);

    @Around("execution (* me.jamc.skeleton.controller.*Controller.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        long startTimeMill = System.currentTimeMillis();

        Object result = pjp.proceed();

        long endTimeMills = System.currentTimeMillis();
        long duration = endTimeMills - startTimeMill;
        LOG.info("request params: url: {}, method: {}, uri: {}, params: {}, duration {}ms",
                url, method, uri, queryString, duration);

        return result;

    }
}
