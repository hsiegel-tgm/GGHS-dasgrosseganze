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

import com.example.myproject2.Variables;



@NamedQueries({
	@NamedQuery(name=Variables.GETEINGELADEN_BYEVENTID,query="FROM Eingeladen ein WHERE ein.event.ID = :id"),
	@NamedQuery(name="getEingeladenforSpecificUser",query="SELECT ein.event FROM Eingeladen ein WHERE ein.user.ID = :id"),
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
	
	private boolean hat_abgestimmt; //TODO inkonsistent

	
	public Eingeladen(User user, DoodleEvent event) {
		super();
		this.user = user;
		this.event = event;
		this.hat_abgestimmt=false;
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
	
	public boolean getAbgestimmt(){
		return hat_abgestimmt;
	}
	public void setAbgestimmt(boolean b){
		this.hat_abgestimmt=b;
	}
	
	public Eingeladen(){}
	
}
