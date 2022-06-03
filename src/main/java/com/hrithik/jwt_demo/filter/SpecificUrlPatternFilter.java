package com.hrithik.jwt_demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/*Not using component for this because then it will be applicable for all urls
@Component*/
public class SpecificUrlPatternFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SpecificUrlPatternFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("Inside SpecificUrlPatternFilter Filter!!");
        filterChain.doFilter(servletRequest,servletResponse);
    }

}
