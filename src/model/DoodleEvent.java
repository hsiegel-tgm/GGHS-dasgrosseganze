package model;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.myproject2.Variables;


@NamedQueries({
	@NamedQuery(name=Variables.GETALLEVENTS,query="FROM DoodleEvent"), 
	@NamedQuery(name=Variables.GETEVENT_BYID,query="FROM DoodleEvent d WHERE  d.ID = :id"),
	@NamedQuery(name=Variables.GETEVENT_BYADMIN,query="FROM DoodleEvent e WHERE  e.user.ID = :id"),
})
@Entity
@Table(name="event")

/**
 * @version 1
 * @Author Hannah Siegel, Daniel Herczeg (Doku)
 *
 * Stellt ein Event dar
 *
**/
public class DoodleEvent {
		
	@Id
	@GeneratedValue
	private Long ID;
	
	@Column(unique=true)
	private String name;
		
	private String ort;
	
	//@OneToOne(optional=false)
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private User user;
	
	private Date fixDatum;
	
	private boolean deleted;

	//@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy ="event")
	//private Collection<Zeit> zeiten = new Vector <Zeit>(); 
	
	//@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy ="event")
	//private Collection<Kommentar> kommentare = new Vector <Kommentar>();
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param name    Der Name des Events
	 * @param ort     Der Ort, an dem das Event stattfinden soll
	 * @param admin   Der Administrator des Events
	 *
	 * Erstellt ein neues Event mit den angegebenen Parametern
	**/
	public DoodleEvent(String name, String ort, User admin) {
		super();
		this.name = name;
		this.ort = ort;
		this.user = admin;
		this.deleted = false;
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param k    Der Kommentar, der hinzugefuegt werden soll.
	 *
	 * Fuegt einen neuen Kommentar hinzu
	**/
	public void addKommentar(Kommentar k){
		//this.kommentare.add(k);
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Den Namen des Events
	 *
	 * Gibt den Namen des Events zurueck
	**/
	public String getName() {
		return name;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param Name    Der neue Name des Eventes
	 *
	 * Gibt den Namen des Events an
	**/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Den Ort des Events
	 *
	 * Gibt den Ort des Events zurueck
	**/	
	public String getOrt() {
		return ort;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param ort    Der neue Wert fuer den Ort
	 *
	 * Setzt den Ort fuer das Event neu
	**/
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Den Namen des Admins
	 *
	 * Gibt den Namen des Admins zurueck
	**/
	public User getAdmin() {
		return user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param admin    Der neue Admin fuer das Event
	 *
	 * Setzt einen neuen Wert fuer den Administrator des Events
	**/
	public void setAdmin(User admin) {
		this.user = admin;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das Datum des Eventes
	 *
	 * Gibt das Datum des Events zurueck
	**/
	public Date getFixDatum() {
		return fixDatum;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param fixDatum    Das fixe Datum fuer das Event
	 *
	 * Setzt ein fixes Datum fuer das Event
	**/
	public void setFixDatum(Date fixDatum) {
		this.fixDatum = fixDatum;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Die Kommentare zu dem Event
	 *
	 * Gibt die Kommentare des Events zurueck
	**/
	public Collection<Kommentar> getKommentare() {
		//return kommentare;
		return null;
	} 
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Die ID des Events
	 *
	 * Gibt die ID des Events zurueck
	**/
	public Long getID(){
		return this.ID;
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Erstellt eine neue Instanz der Klasse. Default-Konstruktor
	**/
	public DoodleEvent(){}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Markiert das Event als geloescht.
	**/
	public void setDeleted(){
		this.deleted=true;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Wert, der angibt, ob das Event als geloescht markiert wurde, oder nicht
	 *
	 * Gibt einen Wert zurueck, der angibt, ob das Event als geloescht markiert wurde, oder nicht
	**/
	public boolean getDeleted(){
		return this.deleted;
	}
}