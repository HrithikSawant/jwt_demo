package com.hrithik.jwt_demo;

import com.hrithik.jwt_demo.filter.SpecificUrlPatternFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtDemoApplication.class, args);
	}

	@Bean
	FilterRegistrationBean<SpecificUrlPatternFilter> specificUrlPatternFilterFilterRegistrationBean(){
		final FilterRegistrationBean<SpecificUrlPatternFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new SpecificUrlPatternFilter());
		filterRegistrationBean.addUrlPatterns("/specific-url-pattern/*");

		return filterRegistrationBean;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
