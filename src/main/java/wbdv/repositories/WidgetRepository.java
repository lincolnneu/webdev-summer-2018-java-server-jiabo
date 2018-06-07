package wbdv.repositories;

import org.springframework.data.repository.CrudRepository;

import wbdv.models.Widget;

public interface WidgetRepository 
extends CrudRepository<Widget, Integer>{

}
