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
import wbdv.models.Topic;
import wbdv.repositories.CourseRepository;
import wbdv.repositories.LessonRepository;
import wbdv.repositories.ModuleRepository;
import wbdv.repositories.TopicRepository;

@RestController
@CrossOrigin(origins="*", maxAge = 3600)
public class TopicService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	TopicRepository topicRepository;
	@PostMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
	public Topic createTopic(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId,
			@PathVariable("lessonId") int lessonId,
			@RequestBody Topic newTopic) {
		Optional<Course> cData = courseRepository.findById(courseId);
		if(cData.isPresent()) {
			Optional<Module> mData = moduleRepository.findById(moduleId);
			if(mData.isPresent()) {
				Optional<Lesson> lData = lessonRepository.findById(lessonId);
				if(lData.isPresent()) {
					Lesson lesson = lData.get();
					newTopic.setLesson(lesson);
					return topicRepository.save(newTopic);
				}
			}
		}
		return null;
	}
	
	@DeleteMapping("/api/topic/{topicId}")
	public void deleteTopic(@PathVariable("topicId") int topicId) {
		topicRepository.deleteById(topicId);
	}
	
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
	public List<Topic> findAllTopicsForLesson(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId,
			@PathVariable("lessonId") int lessonId){
		Optional<Course> cData = courseRepository.findById(courseId);
		if(cData.isPresent()) {
			Optional<Module> mData = moduleRepository.findById(moduleId);
			if(mData.isPresent()) {
				Optional<Lesson> lData = lessonRepository.findById(lessonId);
				if(lData.isPresent()) {
					Lesson lesson = lData.get();
					return lesson.getTopics();
				}
			}
		}
		return null;
	}
	
	@GetMapping("/api/topic")
	public List<Topic> findAllTopics(){
		return (List<Topic>) topicRepository.findAll();
	}
	

}
