package wbdv.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wbdv.models.Widget;
import wbdv.repositories.WidgetRepository;

@RestController // alows us to map various endpoints to hit the db from a HTTP request
@CrossOrigin(origins = "*") // we are accepting everyone.
public class WidgetService {
	@Autowired
	WidgetRepository repository;
	@GetMapping("/api/widget")
	public List<Widget> findAllWidgets(){ // retrieves all the widgets
		return (List<Widget>) repository.findAll();	
	}
	
	@PostMapping("/api/widget/save")
	public void saveAllWidgets(@RequestBody List<Widget> widgets) {
		repository.deleteAll(); // delete all records before adding data passed from frontend. This 
		// enables changes to less items.
		for(Widget widget:widgets){
			repository.save(widget);
		}
	}
}
