package wbdv.services;

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

import wbdv.models.Course;
import wbdv.models.Lesson;
import wbdv.models.Module;
import wbdv.models.Topic;
import wbdv.models.User;
import wbdv.models.Widget;
import wbdv.repositories.CourseRepository;
import wbdv.repositories.LessonRepository;
import wbdv.repositories.ModuleRepository;
import wbdv.repositories.TopicRepository;
import wbdv.repositories.WidgetRepository;

@RestController // alows us to map various endpoints to hit the db from a HTTP request
@CrossOrigin(origins = "*") // we are accepting everyone.
public class WidgetService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	TopicRepository topicRepository;
	@Autowired
	WidgetRepository repository;
	@GetMapping("/api/widget")
	public List<Widget> findAllWidgets(){ // retrieves all the widgets
		return (List<Widget>) repository.findAll();	
	}
	
	@GetMapping("/api/widget/{widgetId}")
	public Widget findWidget(
			@PathVariable("widgetId") int widgetId){
		Optional<Widget> wData = repository.findById(widgetId);
		if(wData.isPresent()) {
			Widget widget = wData.get();
			return widget;
		}
		return null;	
	}
	
	
	@GetMapping("/api/topic/{topicId}/widget")
	public List<Widget> findAllWidgetsForTopic(
			@PathVariable("topicId") int topicId){ // retrieves all the widgets
			Optional<Topic> tData = topicRepository.findById(topicId);
			if(tData.isPresent()) {
				Topic topic = tData.get();
				return topic.getWidgets();
			}
		return null;
	}
	
	@PostMapping("/api/topic/{topicId}/widget/save")
	public void saveAllWidgets(
			@PathVariable("topicId") int topicId,
			@RequestBody List<Widget> widgets) {
		Optional<Topic> tData = topicRepository.findById(topicId);
		if(tData.isPresent()) {
			Topic topic = tData.get();
			for(Widget widget: topic.getWidgets()) {
				repository.delete(widget);
			}
			for(Widget widget:widgets){
				widget.setTopic(topic);
				repository.save(widget);
			}
		}
	}
	
	@PostMapping("/api/topic/{topicId}/widget")
	public Widget createWidgetForTopic(
			@PathVariable("topicId") int topicId,
			@RequestBody Widget newWidget) {
		Optional<Topic> tData = topicRepository.findById(topicId);
		if(tData.isPresent()) {
			Topic topic = tData.get();
			newWidget.setTopic(topic);
			return repository.save(newWidget);
		}
		return null;
	}
	
	
	// a put mapping
	@PutMapping("/api/widget/{widgetId}")
	public ResponseEntity<?> updateWidget(
			@PathVariable("widgetId") int widgetId,
			@RequestBody Widget newWidget) { // RequestBody map it to a user object newUser
		Widget record = findWidget(widgetId);
		if(record != null) {
			record.setItems(newWidget.getItems());
			record.setName(newWidget.getName());
			record.setSize(newWidget.getSize());
			record.setSrc(newWidget.getSrc());
			record.setText(newWidget.getText());
			record.setTopic(newWidget.getTopic());
			record.setWidgetType(newWidget.getWidgetType());
			record.setListType(newWidget.getListType());
			record.setPosition(newWidget.getPosition());
			repository.save(record);
			return new ResponseEntity<User>(HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST); //appropriate error code
		}
	    
	}
	
	@DeleteMapping("/api/widget/{widgetId}")
	public void deleteWidget(@PathVariable("widgetId") int widgetId) {
		repository.deleteById(widgetId);
	}
	
}
