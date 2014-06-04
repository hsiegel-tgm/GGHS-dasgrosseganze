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
	
	public DoodleEvent(String name, String ort, User admin) {
		super();
		this.name = name;
		this.ort = ort;
		this.user = admin;
		this.deleted = false;
	}
	
	public void addKommentar(Kommentar k){
		//this.kommentare.add(k);
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
		return user;
	}

	public void setAdmin(User admin) {
		this.user = admin;
	}

	public Date getFixDatum() {
		return fixDatum;
	}

	public void setFixDatum(Date fixDatum) {
		this.fixDatum = fixDatum;
	}

	public Collection<Kommentar> getKommentare() {
		//return kommentare;
		return null;
	} 
	
	public Long getID(){
		return this.ID;
	}
	
	public DoodleEvent(){}
	
	public void setDeleted(){
		this.deleted=true;
	}
	public boolean getDeleted(){
		return this.deleted;
	}
	
}
