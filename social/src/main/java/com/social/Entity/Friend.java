package com.social.Entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Friend {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	
	@JsonBackReference
	private User user;
	
	@ManyToOne
	@JoinColumn(name="friend_id")
	private User friend;
	
	
	
	private String status;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public User getFriend() {
		return friend;
	}


	public void setFriend(User friend) {
		this.friend = friend;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Friend(Long id, User user, User friend, String status) {
		super();
		this.id = id;
		this.user = user;
		this.friend = friend;
		this.status = status;
	}


	public Friend() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Friend [id=" + id + ", user=" + user + ", friend=" + friend + ", status=" + status + "]";
	}
	
	
}
