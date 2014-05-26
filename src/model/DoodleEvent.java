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
public class DoodleEvent {
	@Id
	@GeneratedValue
	private Long ID;
	
	@Column(unique=true)
	private String name;
		
	private String ort;
	
	@OneToOne(optional=false)
	private User admin;
	
	private Date fixDatum;
	
	//@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy ="event")
	//private Collection<Zeit> zeiten = new Vector <Zeit>(); 
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy ="event")
	private Collection<Kommentar> kommentare = new Vector <Kommentar>();
	
	public DoodleEvent(String name, String ort, User admin) {
		super();
		this.name = name;
		this.ort = ort;
		this.admin = admin;
	}
	
	public void addKommentar(Kommentar k){
		this.kommentare.add(k);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Collection<Kommentar> getKommentare() {
		return kommentare;
	} 
	
	
	
}
