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

	public Event(String name, Date datum, String ort, User admin) {
		super();
		this.name = name;
		this.datum = datum;
		this.ort = ort;
		this.admin = admin;
		this.deleted = false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public Date getFixDatum() {
		return fixDatum;
	}

	public void setFixDatum(Date fixDatum) {
		this.fixDatum = fixDatum;
	}

	public void setDeleted(){
		this.deleted=true;
	}
	
	
}
