package com.hrithik.jwt_demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(3)
public class ResponseFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("Inside ResponseLogging Filter!!");
        filterChain.doFilter(servletRequest,servletResponse);
    }

}
