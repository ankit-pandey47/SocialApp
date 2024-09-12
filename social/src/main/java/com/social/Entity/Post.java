package com.social.Entity;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Post{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id" ,  nullable=false)
	@JsonIgnore
	private User user;
	
	private String dop; //date of photo
	
    @Size(max = 120000)
	@Column(columnDefinition = "LONGTEXT")
	private String text;
	
	private String imageurl;

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

	public String getDop() {
		return dop;
	}

	public void setDop(String dop) {
		this.dop = dop;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public Post(Long id, User user, String dop, String text, String imageurl) {
		super();
		this.id = id;
		this.user = user;
		this.dop = dop;
		this.text = text;
		this.imageurl = imageurl;
	}

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", user=" + user + ", dop=" + dop + ", text=" + text + ", imageurl=" + imageurl + "]";
	}
	
	
	
}