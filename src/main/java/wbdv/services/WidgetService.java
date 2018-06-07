package wbdv.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import wbdv.models.Widget;
import wbdv.repositories.WidgetRepository;

@RestController // alows us to map various endpoints to hit the db from a HTTP request
public class WidgetService {
	@Autowired
	WidgetRepository repository;
	public List<Widget> findAllWidgets(){ // retrieves all the widgets
		return (List<Widget>) repository.findAll();	
	}
}
