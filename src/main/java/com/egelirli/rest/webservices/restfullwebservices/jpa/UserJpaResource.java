package com.egelirli.rest.webservices.restfullwebservices.jpa;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.egelirli.rest.webservices.restfullwebservices.user.Post;
import com.egelirli.rest.webservices.restfullwebservices.user.User;
import com.egelirli.rest.webservices.restfullwebservices.user.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	private UserJpaRepository userRepo;
	private PostJpaRepository postRepo;

	public UserJpaResource(UserJpaRepository service,
						   PostJpaRepository postRepo) {
		this.userRepo = service;
		this.postRepo = postRepo;
	}

	// GET /users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userRepo.findAll();
	}

	// GET /users
	@GetMapping("/users/{id}")
	public EntityModel<Optional<User>> retrieveUser(@PathVariable int userId) {
		
		Optional<User> user = userRepo.findById(userId);
		
		if(user.isPresent()) {
			EntityModel<Optional<User>> entityModel = EntityModel.of(user);
			
			WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
			entityModel.add(link.withRel("all-users"));
			
			return entityModel;			
		}else {
			throw new UserNotFoundException("Id:" + userId);
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}	

	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveAllPostsForUser(
							@PathVariable int id) {
		
		Optional<User> user = userRepo.findById(id);
		
		if(user.isPresent()) {
			
			return  user.get().getPostList();
//			
//			EntityModel<Optional<Post>> entityModel = EntityModel.of(postList);
//			
//			WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
//			entityModel.add(link.withRel("all-users"));
//			
//			return entityModel;					
		}else {
			throw new UserNotFoundException("Id:" + id);
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}	

	@GetMapping("/users/{userId}/posts/{postId}")
	public Post retrievePost(
							@PathVariable int userId, 
							@PathVariable int postId) {
		
		Optional<User> user = userRepo.findById(userId);
		
		if(user.isPresent()) {
			
			Optional<Post> post = user.get().findPost(postId);
			if(post.isPresent()) {
				return post.get();
			}else {
				throw new UserNotFoundException("PostId:" + postId);
			}
			
		}else {
			throw new UserNotFoundException("Id:" + userId);
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}	
	
	
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		
		userRepo.deleteById(id);
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
		
		User savedUser = userRepo.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();   
		
		return ResponseEntity.created(location).build();
	}
	
	
	@PostMapping("/users/{userId}/posts")
	public ResponseEntity<Object> createPost(
						@PathVariable int userId,
						@Valid @RequestBody Post post) {
		
	
		Optional<User> user = userRepo.findById(userId);
		
		if(user.isPresent()) {
			
			post.setUser(user.get());
			Post savedPost = postRepo.save(post);

			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedPost.getId())
							.toUri();   
			
			return ResponseEntity.created(location).build();
		}else {
			throw new UserNotFoundException("Id:" + userId);
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}		
		
		

	}
	
}
