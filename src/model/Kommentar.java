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
/**
 * @version 1
 * @Author Hannah Siegel, Daniel Herczeg (Doku)
 *
 * Stellt einen Kommentar dar
**/
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
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das Event
	 *
	 * Gibt das Event zurueck, zu dem der Kommentar verfasst wurde
	**/
	public DoodleEvent getEvent() {
		return event;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param event    Setzt das neue Event fuer den Kommentar
	 *
	 * Setzt ein Event zu einem Kommentar neu
	**/
	public void setEvent(DoodleEvent event) {
		this.event = event;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der Text des Kommentares
	 *
	 * Gibt den Text zurueck
	**/
	public String getText() {
		return text;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param text    Der Text des Kommentares
	 *
	 * Setzt den Text des Kommentares neu
	**/
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das Datum, an dem der Kommentar verfasst wurde
	 *
	 * Gibt das Datum zurueck, an dem der Kommentar verfasst wurde
	**/
	public Date getGepostet() {
		return gepostet;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param gepostet    das Datum
	 *
	 * setzt das Datum, an dem der Kommentar gepostet wurde
	**/
	public void setGepostet(Date gepostet) {
		this.gepostet = gepostet;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return der Benutzer
	 *
	 * Gibt den Benutzer zurueck, der den Kommentar verfasst hat
	**/
	public User getUser() {
		return user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param user    Der User
	 *
	 * setzt den User, der den Kommentar gepostet hat
	**/
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return die ID
	 *
	 * Gibt die ID zurueck, der dem Kommentar zugeordnet ist
	**/
	public Long getID() {
		return ID;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param event    Das Event, zu dem der Kommentar gehoert
	 * @param text     Der Text des Kommentares
	 * @param gepostet Das Datum, an dem der Kommentar verfasst wurde
	 * @param user     Der User, der den Kommentar verpasst hat
	 *
	 * Erstellt einen neuen Kommentar
	**/
	public Kommentar(DoodleEvent event, String text, Date gepostet, User user) {
		super();
		this.event = event;
		this.text = text;
		this.gepostet = gepostet;
		this.user = user;
		this.deleted=false;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Default-Konstruktor
	**/
	public Kommentar(){}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Setzt den Kommentar als geloescht
	**/
	public void setDeleted(){
		this.deleted=true;
	}
	
	public boolean getDeleted(){
		return this.deleted;
	}
}
