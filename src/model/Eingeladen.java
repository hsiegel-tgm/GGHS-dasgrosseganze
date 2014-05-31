package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;



@NamedQueries({
	@NamedQuery(name="getEingeladenforSpecificEvent",query="FROM Eingeladen ein WHERE ein.event.ID = :id"),
})
@Entity
public class Eingeladen {
	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private DoodleEvent event;	
	
	public Eingeladen(User user, DoodleEvent event) {
		super();
		this.user = user;
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DoodleEvent getEvent() {
		return event;
	}

	public void setEvent(DoodleEvent event) {
		this.event = event;
	}
	
	public Eingeladen(){}
	
}
