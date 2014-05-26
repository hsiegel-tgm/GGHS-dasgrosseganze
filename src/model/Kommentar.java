package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Kommentar {
	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Event event;
	
	private String text;
	
	private Date gepostet;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private User user;
	
}
