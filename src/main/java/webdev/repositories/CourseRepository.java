package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Course;

public interface CourseRepository extends CrudRepository<Course, Integer>{

}
