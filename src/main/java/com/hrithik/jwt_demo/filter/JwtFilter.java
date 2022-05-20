package com.hrithik.jwt_demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrithik.jwt_demo.entity.User;
import com.hrithik.jwt_demo.service.UserService;
import com.hrithik.jwt_demo.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/v1/auth/authenticate")) {
            filterChain.doFilter(request, response);
        } else {
            String headerAuth = request.getHeader("Authorization");
            String authToken;
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                authToken = headerAuth.substring(7);
                try {
                    if (jwtUtility.validateToken(authToken)) {
                        User user = userService.loadUserByUserId(Long.valueOf(jwtUtility.getUserIdFromToken(authToken)));
                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            //contract
                            Collection<SimpleGrantedAuthority> authority = new ArrayList<>();
                            authority.add(new SimpleGrantedAuthority(user.getUserType()));
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                    = new UsernamePasswordAuthenticationToken(user.getEmailId(),
                                    user.getPassword(), authority);
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                        filterChain.doFilter(request, response);
                    }
                } catch (Exception e) {
                    logger.error("Error logging in: {}" + e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", e.getMessage());
                    response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                logger.error("Error logging in: {Authorization header needed}");
                filterChain.doFilter(request, response);
            }
        }

    }
}