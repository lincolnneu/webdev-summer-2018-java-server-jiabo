package wbdv.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wbdv.models.Assignment;
import wbdv.models.Topic;
import wbdv.models.Widget;
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
			List<Assignment> assignments = new ArrayList<Assignment>(); 
			List<Widget> widgets = topic.getWidgets();
			for(Widget widget: widgets) {
				if(widget.getWidgetType().equals("Assignment")) {
					assignments.add((Assignment) widget);
				}
			}
			return assignments;
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
			repository.save(newAssignment);
			return newAssignment;
		}
		return null;
	}
	
	@PutMapping("/api/assignment/{aId}")
	public ResponseEntity<?> updateAssignment(
			@PathVariable("aId") int aId,
			@RequestBody Assignment newAssignment) {
		Optional <Assignment> data = repository.findById(aId);
		if(data.isPresent()) {
			Assignment assignment = data.get();
			assignment.setTitle(newAssignment.getTitle());
			assignment.setDescription(newAssignment.getDescription());
			assignment.setPoints(newAssignment.getPoints());
			repository.save(assignment);
			return new ResponseEntity<Assignment>(HttpStatus.OK);
		}
		return new ResponseEntity<Assignment>(HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping("/api/assignment/{aId}")
	public void deleteAssignment(@PathVariable("aId") int aId) {
		repository.deleteById(aId);
	}
	
}
