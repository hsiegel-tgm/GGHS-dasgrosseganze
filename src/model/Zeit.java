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



/**
 * @author Hannah Siegel
 * @version 2014-05-26 
 * 
 * TODO JUnit
 * TODO Komment
 * 
 * TODO Coding style
 * TODO Endzeit
 * */

@NamedQueries({
	@NamedQuery(name="getTimePossibilitesforSpecificEvent",query="FROM Zeit z WHERE z.event.ID = :id"),
})

@Entity
/**
 * @version 1
 * @Author Hannah Siegel, Daniel Herczeg (Doku)
 *
 * Stellt eine Zeit dar
**/
public class Zeit {
	@Id
	@GeneratedValue
	private Long ID;
	private Date anfang;
	private Date ende;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private DoodleEvent event;

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der Beginn des Events
	 *
	 * Gibt die Zeit des Beginns zurueck
	**/
	public Date getAnfang() {
		return anfang;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param anfang    Der Beginn des Events
	 *
	 * Setzt den Anfang des Events
	**/
	public void setAnfang(Date anfang) {
		this.anfang = anfang;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das Ende des Eventes
	 *
	 * Gibt das Ende des Eventes zurueck
	**/
	public Date getEnde() {
		return ende;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param ende    das Ende des Eventes
	 *
	 * Setzt das Ende des Eventes
	**/
	public void setEnde(Date ende) {
		this.ende = ende;
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
	 * @param event    Das Event
	 *
	 * Setzt das Event
	**/
	public void setEvent(DoodleEvent event) {
		this.event = event;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param anfang    Die Anfangszeit des Events
	 * @param ende      Das Ende des Eventes
	 * @param event     Das Event
	 *
	 * Erstellt eine neue Zeit fuer das Event
	**/
	public Zeit(Date anfang, Date ende, DoodleEvent event) {
		super();
		this.anfang = anfang;
		this.ende = ende;
		this.event = event;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Die ID
	 *
	 * Gibt die ID zurueck
	**/
	public Long getID(){
		return this.ID;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Standard-Konstruktor
	**/
	public Zeit(){}
}