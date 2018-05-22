package webdev.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import webdev.models.User;
import webdev.repositories.UserRepository;

//mapped to some URL
@RestController 
public class UserService {
//retrieve all the users
	@Autowired // instead of us instantiating repo, we ask a framework to inject it into that variable
	UserRepository repository;
	
	@DeleteMapping("/api/user/{userId}") //--->|
	public void deleteUser(@PathVariable("userId") int id) { // parse the userId to an int
		repository.deleteById(id);
	}
	
	// enable post
	@PostMapping("/api/user")
	public User createUser(@RequestBody User user) {
		return repository.save(user); // generate a insert into db. Return the user instance
	}
	
	// enable post
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) {
		if(findUserByUserName(user.getUsername()) == null) {
			System.out.println("We don't have this entry. Accept");
			User newUser =  createUser(user);
			session.setAttribute("currentUser", newUser);
			return newUser;
		} else {
			System.out.println("Yes we have. Reject");
			return null;
		}
	}
	
	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		User currentUser = (User)
		session.getAttribute("currentUser");
//		System.out.println(currentUser.getUsername());
		return currentUser;
	}

	@PostMapping("/api/logout")
	public void logout
	(HttpSession session) {
		session.invalidate();
		System.out.println("=============== log out success=================");
	}
	
	@PostMapping("/api/login")
	public User login(	@RequestBody User credentials, HttpSession user) {
		Optional <User> data = repository.findUserByUserNameAndPassword(credentials.getUsername(), credentials.getPassword());
		if(data.isPresent()) {
			User foundUser = data.get();
			user.setAttribute("currentUser", foundUser);
			System.out.println("===============72 login=================");
			System.out.println(foundUser.getUsername());
			System.out.println(user.getAttribute("currentUser"));
			System.out.println("===============74 login=================");
			return foundUser;
		} else {
			System.out.println("===============77 login failed=================");
			return null;
		}
		 
	}

	// exe this function when I ask for all users. Enable get
	@GetMapping("/api/user")  // this is mapped to a get request
	public List<User> findAllUsers(){
		return (List<User>) repository.findAll(); // select * to user
	}
	
	// a put mapping
	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) { // RequestBody map it to a user object newUser
		// retrieve the object in the db
		Optional<User> data = repository.findById(userId); // findById could return null, so we should declare it as Optional from util
		if(data.isPresent()) {
			User user = data.get();
			user.setUsername(newUser.getUsername());
			user.setFirstName(newUser.getFirstName()); // local object
			user.setLastName(newUser.getLastName());
			user.setPassword(newUser.getPassword());
			user.setRole(newUser.getRole());
			repository.save(user);
			return user;
		}
		return null;
	}
	
	// a put mapping
	@PutMapping("/api/profile/updateProfile")
	public User updateProfile(@RequestBody User newUser, HttpSession session) { // RequestBody map it to a user object newUser
		User currentUser = (User)
				session.getAttribute("currentUser");
		if(currentUser != null) {
			currentUser.setPhone(newUser.getPhone());
			currentUser.setEmail(newUser.getEmail()); // local object
			currentUser.setRole(newUser.getRole());
			currentUser.setDateOfBirth(newUser.getDateOfBirth());
			repository.save(currentUser);
			return currentUser;
		}
		return null;
	}
	
	@GetMapping("/api/user/{userId}")  // this is mapped to a get request
	public User findUserById(@PathVariable("userId") int userId){ // parse the userId to an int
		Optional<User> data = repository.findById(userId); // findById could return null, so we should declare it as Optional from util
		if(data.isPresent()) {
			return data.get(); //If a value is present in this Optional, returns the value, otherwise throws NoSuchElementException.
		} // is this object not null (present)?
		return null;
	}
	
	@GetMapping("/api/register/{username}")  // this is mapped to a get request
	public User findUserByUserName(@PathVariable("username") String username) {
		System.out.println(username);
		Optional<User> data = repository.findUserByUserName(username); // findUserByUserName could return null, so we should declare it as Optional from util
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
}
