package com.egelirli.rest.webservices.restfullwebservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	private UserDaoService service;

	public UserResource(UserDaoService service) {
		this.service = service;
	}

	// GET /users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	// GET /users
	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		
		User user = service.findOne(id);
		if(user != null) {
			return user;
		}else {
			
			throw new UserNotFoundException("Id:" + id);
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}	

	@DeleteMapping("/users/{id}")
	public Boolean deleteUser(@PathVariable int id) {
		
		return service.deleteUserById(id);
//		if(user != null) {
//			return user;
//		}else {
//			
//			throw new UserNotFoundException("Id:" + id);
//			//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//		}
		
	}	
	
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		
		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();   
		
		return ResponseEntity.created(location).build();
	}
}