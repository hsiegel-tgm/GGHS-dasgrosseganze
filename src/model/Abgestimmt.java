package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.example.myproject2.Variables;


//get Wertung from Abgestimmt where z.id = zeit and u.id = user

@NamedQueries({
	@NamedQuery(name=Variables.GETABGESTIMMT_BYUSER,query="FROM Abgestimmt a WHERE a.user.ID = :id"),
	@NamedQuery(name=Variables.GETABGESTIMMT,query="FROM Abgestimmt a"),
	@NamedQuery(name=Variables.GETWERTUNG,query="SELECT a.wertung FROM Abgestimmt a WHERE a.user.ID = :userid AND a.zeit.ID = :zeitid"),

})

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

	public Abgestimmt(User user, Zeit zeit, boolean wertung) {
		super();
		this.user = user;
		this.zeit = zeit;
		this.wertung = wertung;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Zeit getZeit() {
		return zeit;
	}

	public void setZeit(Zeit zeit) {
		this.zeit = zeit;
	}

	public boolean isWertung() {
		return wertung;
	}

	public void setWertung(boolean wertung) {
		this.wertung = wertung;
	}

	public Long getID() {
		return ID;
	}
	
	public Abgestimmt(){}
	
}
