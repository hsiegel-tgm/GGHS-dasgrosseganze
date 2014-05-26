package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Abgestimmt {
	
	@Id
	@GeneratedValue
	private Long ID;
	
	private User user;
	
	private Zeit zeit;
	
	private boolean wertung;
}
