package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Notification {

	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private User user;
	
	private String text;

	public Notification(){
	}
	
	public Notification(User user, String text) {
		super();
		this.user = user;
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
