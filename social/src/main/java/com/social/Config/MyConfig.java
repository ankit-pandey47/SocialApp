package com.social.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.FilterChain;

@Configuration
@EnableWebSecurity
public class MyConfig {

	


	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailServicempl();
	}
	
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(requests -> requests.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/user/**").hasRole("USER").requestMatchers("/**").permitAll())
		        .formLogin(login -> 
		        login.loginPage("/signin")
		        .loginProcessingUrl("/do-login")
		        .defaultSuccessUrl("/user/myprofile")
		        .usernameParameter("email")
		        .passwordParameter("password"))
		        .csrf(csrf -> csrf.disable());
		
		return http.build();
		
	}
	
	
}
