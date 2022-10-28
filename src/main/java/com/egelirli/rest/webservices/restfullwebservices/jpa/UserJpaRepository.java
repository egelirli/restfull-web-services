package com.egelirli.rest.webservices.restfullwebservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.egelirli.rest.webservices.restfullwebservices.user.User;

public interface UserJpaRepository 
				extends JpaRepository<User, Integer> {

}
