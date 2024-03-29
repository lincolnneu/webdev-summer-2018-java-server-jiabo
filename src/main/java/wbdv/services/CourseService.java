package wbdv.services;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wbdv.models.Course;
import wbdv.models.User;
import wbdv.repositories.CourseRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600) // * means everybody
// browser action 1: pre-flight request. Go to the server: if a javascript wants to talk to you, and it is from
// other domain, is it ok?
// browser action 2: They want to do a get. Are you receiving get from this domain?
public class CourseService {
	@Autowired
	CourseRepository courseRepository;
	
	@GetMapping("/api/course")
	public Iterable<Course> findAllCourses(){
		return courseRepository.findAll();
	}
	
	@PostMapping("/api/course")
	public Course createCourse(@RequestBody Course course, HttpSession session) {
		if(UserService.curUser == null) {
			course.setOwner("Anonymous");
		} else {
			course.setOwner(UserService.curUser.getUsername());
		}
		return courseRepository.save(course);
	}
	
	@DeleteMapping("/api/course/{courseId}")
	public void deleteCourse(@PathVariable("courseId") int id) {
		courseRepository.deleteById(id);
	}
	
	@GetMapping("/api/course/{courseId}")  // this is mapped to a get request
	public Course findCourseById(@PathVariable("courseId") int courseId){ // parse the userId to an int
		Optional<Course> data = courseRepository.findById(courseId); // findById could return null, so we should declare it as Optional from util
		if(data.isPresent()) {
			return data.get(); //If a value is present in this Optional, returns the value, otherwise throws NoSuchElementException.
		} // is this object not null (present)?
		return null;
	}

}
