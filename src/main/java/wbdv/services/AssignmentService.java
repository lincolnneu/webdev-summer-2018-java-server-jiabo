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

import wbdv.models.Assignment;
import wbdv.models.Topic;
import wbdv.repositories.AssignmentRepository;
import wbdv.repositories.TopicRepository;

@RestController
@CrossOrigin(origins="*")
public class AssignmentService {
	@Autowired
	AssignmentRepository repository;
	
	@Autowired
	TopicRepository tRepository;
	
	@GetMapping("/api/assignment")
	public Iterable<Assignment> finAllAssignments(){
		return repository.findAll();
	}
	
	@GetMapping("/api/assignment/{aId}")
	public Assignment findAssignmentById(@PathVariable("aId") int aId) {
		Optional<Assignment> data = repository.findById(aId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/topic/{topicId}/assignment")
	public List<Assignment> findAllAssignmentsForTopic(
			@PathVariable("topicId") int topicId){
		Optional<Topic> data = tRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			return topic.getAssignments();
		}
		return null;
	}
	
	@PostMapping("/api/topic/{topicId}/assignment")
	public Assignment createAssignment(
			@PathVariable("topicId") int topicId,
			@RequestBody Assignment newAssignment) {
		Optional <Topic> data = tRepository.findById(topicId);
		if(data.isPresent()) {
			Topic topic = data.get();
			newAssignment.setTopic(topic);
			return repository.save(newAssignment);
		}
		return null;
	}
	
	@DeleteMapping("/api/assignment/{aId}")
	public void deleteAssignment(@PathVariable("aId") int aId) {
		repository.deleteById(aId);
	}
	
}
