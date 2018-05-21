package webdev.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.User;

public interface UserRepository
		extends CrudRepository<User, Integer>{ 
	//what objects are we storing in the database
	/* Verify if the username is already taken*/
	@Query("SELECT user FROM User user WHERE user.username =:username")
	Optional <User> findUserByUserName(@Param("username") String username);
	
	@Query("SELECT user FROM User user WHERE user.username =:username AND user.password =:password")
	Optional <User> findUserByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
	// findUserByUserNameAndPassword is also called findUserByCredentials, though the nomenclature is no consistent in the description.
}



