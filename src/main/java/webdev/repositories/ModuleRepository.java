package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Module;


public interface ModuleRepository
	extends CrudRepository<Module, Integer>{

}
