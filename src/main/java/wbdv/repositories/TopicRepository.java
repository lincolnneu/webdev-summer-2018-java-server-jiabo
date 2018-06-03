package wbdv.repositories;

import org.springframework.data.repository.CrudRepository;

import wbdv.models.Topic;

public interface TopicRepository 
	extends CrudRepository<Topic, Integer>{

}
