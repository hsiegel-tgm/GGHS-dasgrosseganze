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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="event")
/**
 * @version 1
 * @Author Hannah Siegel, Daniel Herczeg (Doku)
 *
 * Stellt ein Event dar
**/
public class Event {
	@Id
	@GeneratedValue
	private Long ID;
	
	@Column(unique=true)
	private String name;
	private Date datum;
	private String ort;
	
	@OneToOne(optional=false)
	private User admin;
	private Date fixDatum;
	private boolean deleted;

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param name    Der Name fuer das Event
	 * @param datum   Das Datum fuer das Event
	 * @param ort     Der Ort fuer das Event
	 * @param admin   Der Admin des Events
	 *
	 * Erstellt ein neues Event
	**/
	public Event(String name, Date datum, String ort, User admin) {
		super();
		this.name = name;
		this.datum = datum;
		this.ort = ort;
		this.admin = admin;
		this.deleted = false;
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der Name des Events
	 *
	 * Gibt den Namen zurueck
	**/
	public String getName() {
		return name;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param name    Der neue Name fuer das Event
	 *
	 * Setzt den Namen des Events neu
	**/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das Datum
	 *
	 * Gibt das Datum zurueck
	**/
	public Date getDatum() {
		return datum;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param datum    Das neue Datum fuer das Event
	 *
	 * Setzt das Datum fuer das Event neu
	**/
	public void setDatum(Date datum) {
		this.datum = datum;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der Ort
	 *
	 * Gibt den Ort zurueck
	**/
	public String getOrt() {
		return ort;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param ort    Der Ort, an dem das Event stattfinden soll
	 *
	 * Setzt den Ort fuer das Event neu
	**/
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der Admin
	 *
	 * Gibt den Admin zurueck
	**/
	public User getAdmin() {
		return admin;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param admin    der neue Admin
	 *
	 * Setzt den Admin fuer das Event neu
	**/
	public void setAdmin(User admin) {
		this.admin = admin;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das fixe Datum des Events
	 *
	 * Gibt das fixe Datum des Eventes zurueck
	**/
	public Date getFixDatum() {
		return fixDatum;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param fixDatum    das neue fixe Datum
	 *
	 * Setzt das fixe Datum fuer das Event neu
	**/
	public void setFixDatum(Date fixDatum) {
		this.fixDatum = fixDatum;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Setzt das Event als geloescht
	**/
	public void setDeleted(){
		this.deleted=true;
	}
}