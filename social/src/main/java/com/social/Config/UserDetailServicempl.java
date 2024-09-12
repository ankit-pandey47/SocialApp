package com.social.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.social.Entity.User;
import com.social.Repository.UserRepository;

public class UserDetailServicempl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.userRepository.getUserByEmail(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not find the user !!");
		}
		
		System.out.println("ankit");
		
		System.out.println("User found: " + user.getEmail());
	    System.out.println("Stored password: " + user.getPassword());
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
		
	}

}
