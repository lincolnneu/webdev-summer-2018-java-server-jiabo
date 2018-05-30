package webdev.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Course {
	@OneToMany(mappedBy="course")
	private List<Module> modules;
}
