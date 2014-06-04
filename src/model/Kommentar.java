package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.example.myproject2.Variables;


@NamedQueries({
	@NamedQuery(name=Variables.GETKOMMENTS,query="FROM Kommentar k WHERE k.event.ID = :id"),
})

@Entity
public class Kommentar {
	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private DoodleEvent event;
	
	private String text;
	
	private Date gepostet;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private User user;
	
	private boolean deleted;
	
	public DoodleEvent getEvent() {
		return event;
	}


	public void setEvent(DoodleEvent event) {
		this.event = event;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Date getGepostet() {
		return gepostet;
	}


	public void setGepostet(Date gepostet) {
		this.gepostet = gepostet;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Long getID() {
		return ID;
	}


	public Kommentar(DoodleEvent event, String text, Date gepostet, User user) {
		super();
		this.event = event;
		this.text = text;
		this.gepostet = gepostet;
		this.user = user;
		this.deleted=false;
	}

	public Kommentar(){}
	
	public void setDeleted(){
		this.deleted=true;
	}
	
	public boolean getDeleted(){
		return this.deleted;
	}
}
