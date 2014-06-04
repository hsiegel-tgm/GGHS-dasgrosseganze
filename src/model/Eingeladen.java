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
/**
 * @version 1
 * @Author Hannah Siegel, Daniel Herczeg (Doku)
**/
public class Eingeladen {
	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private DoodleEvent event;	
	
	private boolean hat_abgestimmt; //TODO inkonsistent

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param User    Ein User
	 * @param event   Das Event
	 *
	 * Gibt den Benutzer zurueck
	**/
	public Eingeladen(User user, DoodleEvent event) {
		super();
		this.user = user;
		this.event = event;
		this.hat_abgestimmt=false;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der User
	 *
	 * Gibt den Benutzer zurueck
	**/
	public User getUser() {
		return user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param user    Der neue Benutzer
	 *
	 * Setzt den Benutzer
	**/
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das Event
	 *
	 * Gibt das Event zurueck
	**/
	public DoodleEvent getEvent() {
		return event;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param event    Das neue Event
	 *
	 * Setzt das Event
	**/
	public void setEvent(DoodleEvent event) {
		this.event = event;
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Ein Wert, der angibt, ob abgestimmt wurde, oder nicht
	 *
	 * Gibt Einen Wert zurueck, der angibt, ob abgestimmt wurde, oder nicht
	**/
	public boolean getAbgestimmt(){
		return hat_abgestimmt;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param b     Der neue Wert fuer abgestimmt
	 *
	 * Setzt Einen Wert, der angibt, ob abgestimmt wurde, oder nicht
	**/
	public void setAbgestimmt(boolean b){
		this.hat_abgestimmt=b;
	}
	
	public Eingeladen(){}
}