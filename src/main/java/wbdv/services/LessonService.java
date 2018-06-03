package wbdv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wbdv.models.Course;
import wbdv.models.Lesson;
import wbdv.models.Module;
import wbdv.repositories.CourseRepository;
import wbdv.repositories.LessonRepository;
import wbdv.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins="*", maxAge = 3600)
public class LessonService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	LessonRepository lessonRepository;
	@PostMapping("/api/course/{courseId}/module/{moduleId}/lesson")
	public Lesson createLesson(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId,
			@RequestBody Lesson newLesson) {
		Optional<Course> cData = courseRepository.findById(courseId);
		if(cData.isPresent()) {
			Optional<Module> mData = moduleRepository.findById(moduleId);
			if(mData.isPresent()) {
				Module module = mData.get();
				newLesson.setModule(module);
				return lessonRepository.save(newLesson);
			}
		}
		return null;
	}
	
	@DeleteMapping("/api/lesson/{lessonId}")
	public void deleteLesson(@PathVariable("lessonId") int lessonId) {
		lessonRepository.deleteById(lessonId);
	}
	
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson")
	public List<Lesson> findAllLessonsForModule(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId){
		Optional<Course> cData = courseRepository.findById(courseId);
		if(cData.isPresent()) {
			Optional<Module> mData = moduleRepository.findById(moduleId);
			if(mData.isPresent()) {
				Module module = mData.get();
				return module.getLessons();
			}
		}
		return null;
	}
	
	@GetMapping("/api/lesson")
	public List<Lesson> findAllLessons(){
		return (List<Lesson>) lessonRepository.findAll();
	}
	
}
