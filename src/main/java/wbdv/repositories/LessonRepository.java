package wbdv.repositories;

import org.springframework.data.repository.CrudRepository;

import wbdv.models.Lesson;

public interface LessonRepository 
	extends CrudRepository<Lesson, Integer>{

}
