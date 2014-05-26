package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Eingeladen {
	@Id
	@GeneratedValue
	private Long ID;
	
	@OneToOne(optional=false)
	private User user;
	
	@OneToOne(optional=false)
	private DoodleEvent event;
	
}
