package com.egelirli.rest.webservices.restfullwebservices.user;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Post {

	
	
	@Id
	@GeneratedValue
	private int id;
	
	@Size(min = 10, message = "Description must have min 10 chars")
	private String description;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	
	
	public Post() {

	}

	public Post(String description) {
		super();
		this.description = description;
	}

	
	
	public void setId(int id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setUser(User user) {
		this.user = user;
		
	}
	
	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}

	
	
	
}
