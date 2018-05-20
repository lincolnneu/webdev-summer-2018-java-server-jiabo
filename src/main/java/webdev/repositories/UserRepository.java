package webdev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import webdev.models.User;
public interface UserRepository extends CrudRepository<User, Integer> {
	//what objects we are storing in the database
}
