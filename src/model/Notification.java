package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
/**
 * @version 1
 * @Author Hannah Siegel, Daniel Herczeg (Doku)
 *
 * Stellt eine Notification dar
**/
public class Notification {

	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private User user;
	
	private String text;

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * 
	 * Default-Konstruktor
	**/
	public Notification(){
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param user    Der Benutzer, fuer den die Benachrichtigung ist
	 * @param text    Der Text der Notification
	 * 
	 * Erstellt eine neue Instanz der Klasse
	**/
	public Notification(User user, String text) {
		super();
		this.user = user;
		this.text = text;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der Benutzer
	 *
	 * Gibt den Benutzer zurueck, fuer den die Notification ist
	**/
	public User getUser() {
		return user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param user    Der Benutzer
	 *
	 * Setzt den Benutzer fuer die Benachrichtigung
	**/
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der Text der Nachricht
	 *
	 * Gibt den Text der Benachrichtigung zurueck
	**/
	public String getText() {
		return text;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param text    Der Text der Benachrichtigung
	 *
	 * Setzt den Text der Notification neu
	**/
	public void setText(String text) {
		this.text = text;
	}
}