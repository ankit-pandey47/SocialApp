package com.social.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.social.Entity.User;
import com.social.Helper.Message;
import com.social.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class myController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserRepository UserRepository;
	

	@GetMapping("/signup")
	public String signup(Model model , HttpSession session) {
		session.removeAttribute("message");
		model.addAttribute("title" , "Signup");
		model.addAttribute("user", new User());
		return "signup";
	}
	
//	@GetMapping("/username/{query}")
//	public ResponseEntity<?> checkUsername(@PathVariable("query")String query){
//		
//		List<User>users = this.UserRepository.findByUsernameContaining(query);
//		System.out.println("Search Query: " + query);
//
//		System.out.println(users);
//		
//		return ResponseEntity.ok(users);
//	}
	
	@PostMapping("/process-signup")
	public String addUser(@ModelAttribute("user")User user , HttpSession session) {
		session.removeAttribute("message");
		
		  User user1 = this.UserRepository.getUserByUsername(user.getUsername());
		  if(user1!=null) {
			  session.setAttribute("message", new Message("username already exists!!", "alert-danger"));
			  return "signup";
		  }
		  
		  
		  user.setRole("USER");
		  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		  User saveduser = this.UserRepository.save(user);
		  System.out.println(saveduser);
		
		  User user3 = this.UserRepository.getUserByEmail(saveduser.getEmail());
		  System.out.println(user3);
		  
		return "login";
	}
	
	
	
	@GetMapping("/signin")
	public String Signin(Model model) {
		model.addAttribute("title","Login-Smart Contact Manager");
		return "login";
	}
	
	

	
}
