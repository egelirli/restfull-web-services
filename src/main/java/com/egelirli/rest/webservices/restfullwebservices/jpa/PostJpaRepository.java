package com.egelirli.rest.webservices.restfullwebservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.egelirli.rest.webservices.restfullwebservices.user.Post;
import com.egelirli.rest.webservices.restfullwebservices.user.User;

public interface PostJpaRepository 
				extends JpaRepository<Post, Integer> {

}
