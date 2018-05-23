package webdev.services;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
	
    @Autowired
    private JavaMailSender sender;

    @PostMapping("/api/forgotPassword")
    public ResponseEntity<?> forgotPassword(HttpServletRequest request, @RequestBody User user) {
    	User record = findUserByEmail(user.getEmail());
    	if( record != null) {
    		try {
    	    	String url = request.getRequestURL().toString();
    	        sendEmail(url, record);
    	        return new ResponseEntity<User>(HttpStatus.OK);
    	    } catch(Exception ex) {
    	    	return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    	    }

    	} else {
//    		System.out.println("the email does not match any record");
    		 return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    	}
    }    

    private void sendEmail(String url, User record) throws Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        helper.setTo(record.getEmail());
        helper.setText("Please go to this link to reset your password: " + url.substring(0, url.length() - 19) + "/jquery/components/password/resetPassword.template.client.html");
        helper.setSubject("Reset your password");
        
        sender.send(message);

    }
	
	
	@DeleteMapping("/api/user/{userId}") 
	public void deleteUser(@PathVariable("userId") int id) { // parse the userId to an int
		repository.deleteById(id);
	}
	
	// enable post
	@PostMapping("/api/user")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		// if the username is already in use, return failure.
		User record = findUserByUserName(user.getUsername());
		if(record != null) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		} else {
			repository.save(user);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
	}
	
	// enable post
	@PostMapping("/api/register")
	public ResponseEntity<?> register(@RequestBody User user, HttpSession session) {
		if(findUserByUserName(user.getUsername()) == null) {
//			System.out.println("We don't have this entry. Accept");
			createUser(user);
			User newUser = findUserByUserName(user.getUsername());
			session.setAttribute("currentUser", newUser);
			return new ResponseEntity<User>(HttpStatus.OK);
		} else {
//			System.out.println("Yes we have. Reject");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		User currentUser = (User)
		session.getAttribute("currentUser");
		return currentUser;
	}

	@PostMapping("/api/logout")
	public void logout
	(HttpSession session) {
		session.invalidate();
	}
	
	@PostMapping("/api/login")
	public ResponseEntity<?> login(	@RequestBody User credentials, HttpSession user) {
		Optional <User> data = repository.findUserByUserNameAndPassword(credentials.getUsername(), credentials.getPassword());
		if(data.isPresent()) {
			User foundUser = data.get();
			user.setAttribute("currentUser", foundUser);
			return new ResponseEntity<User>(HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		 
	}

	// exe this function when I ask for all users. Enable get
	@GetMapping("/api/user")  // this is mapped to a get request
	public List<User> findAllUsers(){
		return (List<User>) repository.findAll(); // select * to user
	}
	
	// a put mapping
	@PutMapping("/api/user/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) { // RequestBody map it to a user object newUser
		// retrieve the object in the db
		Optional<User> data = repository.findById(userId); // findById could return null, so we should declare it as Optional from util
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(newUser.getFirstName()); // local object
			user.setLastName(newUser.getLastName());
			user.setPassword(newUser.getPassword());
			user.setRole(newUser.getRole());
			repository.save(user);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	
	// a put mapping
	@PutMapping("/api/profile/updateProfile")
	public ResponseEntity<?> updateProfile(@RequestBody User newUser, HttpSession session) { // RequestBody map it to a user object newUser
		User currentUser = (User)
				session.getAttribute("currentUser");
		if(currentUser != null) {
			currentUser.setFirstName(newUser.getFirstName());
			currentUser.setLastName(newUser.getLastName());
			currentUser.setPhone(newUser.getPhone());
			currentUser.setEmail(newUser.getEmail()); // local object
			currentUser.setRole(newUser.getRole());
			currentUser.setDateOfBirth(newUser.getDateOfBirth());
			repository.save(currentUser);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	
	// a put mapping
	@PutMapping("/api/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody User newUser) { // RequestBody map it to a user object newUser
		User record = findUserByUserName(newUser.getUsername());
		if(record != null) {
			record.setPassword(newUser.getPassword());
			repository.save(record);
			return new ResponseEntity<User>(HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST); //appropriate error code
		}
	    
	}
	
	@GetMapping("/api/user/{userId}")  // this is mapped to a get request
	public User findUserById(@PathVariable("userId") int userId){ // parse the userId to an int
		Optional<User> data = repository.findById(userId); // findById could return null, so we should declare it as Optional from util
		if(data.isPresent()) {
			return data.get(); //If a value is present in this Optional, returns the value, otherwise throws NoSuchElementException.
		} // is this object not null (present)?
		return null;
	}
	
	@GetMapping("/api/selectUserName/{username}")  // this is mapped to a get request
	public User findUserByUserName(@PathVariable("username") String username) {
//		System.out.println(username);
		Optional<User> data = repository.findUserByUserName(username); // findUserByUserName could return null, so we should declare it as Optional from util
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/email/{email}")  // this is mapped to a get request
	public User findUserByEmail(@PathVariable("email") String email) {
		Optional<User> data = repository.findUserByEmail(email); // findUserByUserName could return null, so we should declare it as Optional from util
		if(data.isPresent()) {
			return data.get();
		}
//		System.out.println("cannot find a user with the email, return null");
		return null;
	}
	
}
