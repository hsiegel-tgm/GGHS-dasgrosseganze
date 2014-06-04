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


//get Wertung from Abgestimmt where z.id = zeit and u.id = user

@NamedQueries({
	@NamedQuery(name=Variables.GETABGESTIMMT_BYUSER,query="FROM Abgestimmt a WHERE a.user.ID = :id"),
	@NamedQuery(name=Variables.GETABGESTIMMT_BYEVENTID,query="FROM Abgestimmt a WHERE a.zeit.ID = :id"), //TODO zeit id
	@NamedQuery(name=Variables.GETABGESTIMMT,query="FROM Abgestimmt a"),
	@NamedQuery(name=Variables.GETWERTUNG,query="SELECT a.wertung FROM Abgestimmt a WHERE a.user.ID = :userid AND a.zeit.ID = :zeitid"),
	@NamedQuery(name=Variables.GETABGESTIMMT_BYIDEVENT,query="FROM Abgestimmt a WHERE a.user.ID = :userid AND a.zeit.ID = :zeitid"),

})

@Entity
/**
 * @author Hannah Siegel, Daniel Herczeg (Doku)
 * @version 2014-06-04
 * 
 * Abgestimmt.java
**/
public class Abgestimmt {
	
	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Zeit zeit;
	
	private boolean wertung;

	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Erstellt eine neue Instanz der Klasse
	 * @param user    Der Benutzer
	 * @param zeit    Die Zeit, zu der abgestimmt wurde
	 * @param wertung Die Auswahl, die der User ausgewaehlt hat.
	**/
	public Abgestimmt(User user, Zeit zeit, boolean wertung) {
		super();
		this.user = user;
		this.zeit = zeit;
		this.wertung = wertung;
	}

	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Gibt den Benutzer zurueck
	 * @return Den Wert, der fuer den Benutzer gespeichert wurde
	**/
	public User getUser() {
		return user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Setzt einen neuen Wert fuer den Benutzer
	 * @param user    Der neue Wert fuer den Benutzer
	**/
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Gibt die Zeit zurueck
	 * @return Den Wert, der fuer die Zeit gespeichert wurde
	**/
	public Zeit getZeit() {
		return zeit;
	}

	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Setzt einen neuen Wert fuer die Zeit
	 * @param user    Der neue Wert fuer die Zeit
	**/
	public void setZeit(Zeit zeit) {
		this.zeit = zeit;
	}

	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Gibt die Wertung zurueck
	 * @return Den Wert, der fuer die Wertung gespeichert wurde
	**/
	public boolean isWertung() {
		return wertung;
	}

	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Setzt einen neuen Wert fuer die Wertung
	 * @param user    Der neue Wert fuer die Wertung
	**/
	public void setWertung(boolean wertung) {
		this.wertung = wertung;
	}

	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Gibt die ID zurueck
	 * @return Den Wert, der fuer die ID gespeichert wurde
	**/
	public Long getID() {
		return ID;
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 2014-05-29
	 *
	 * Default-Konstruktor
	**/	
	public Abgestimmt(){}
}