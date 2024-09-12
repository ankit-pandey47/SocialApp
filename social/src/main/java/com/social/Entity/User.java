package com.social.Entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;
    
    private String role;
    
    private String profession;
    
    private String profile_img;
    
    private int connection_count;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
   @JsonIgnore
    private Set<Friend> friends = new HashSet<>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
   public Set<Post> posts = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getProfile_img() {
		return profile_img;
	}

	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}

	public Set<Friend> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friend> friends) {
		this.friends = friends;
	}
	

	public int getConnection_count() {
		return connection_count;
	}

	public void setConnection_count(int connection_count) {
		this.connection_count = connection_count;
	}
	
	

	
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public User(Long id, String username, String email, String password, String role, String profession,
			String profile_img, int connection_count, Set<Friend> friends, Set<Post> posts) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.profession = profession;
		this.profile_img = profile_img;
		this.connection_count = connection_count;
		this.friends = friends;
		this.posts = posts;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + "]";
//	}
//
//  
	

	
	


	

    // Constructors
    
    
    
}
