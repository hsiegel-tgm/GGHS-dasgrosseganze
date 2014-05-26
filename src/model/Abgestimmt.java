package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Abgestimmt {
	
	@Id
	@GeneratedValue
	private Long ID;
	
	@OneToOne(optional=false)
	private User user;
	
	@OneToOne(optional=false)
	private Zeit zeit;
	
	private boolean wertung;
}
