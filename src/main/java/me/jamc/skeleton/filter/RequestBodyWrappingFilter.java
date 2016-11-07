package me.jamc.skeleton.filter;

import com.google.common.io.ByteStreams;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by Jamc on 11/3/16.
 *
 * Making stream in request body re-readable.
 */
@WebFilter
public class RequestBodyWrappingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        if ("POST".equals(req.getMethod()) || "PUT".equals(req.getMethod())) {
            ACHServletRequestWrapper wrapper = new ACHServletRequestWrapper((HttpServletRequest)request);
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    class ACHServletRequestWrapper extends HttpServletRequestWrapper {

        private final byte[] body;

        public ACHServletRequestWrapper(HttpServletRequest request)
                throws IOException {
            super(request);
            body = ByteStreams.toByteArray(request.getInputStream());
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            return new ServletInputStream() {

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener arg0) {
                }
            };
        }
    }
}
