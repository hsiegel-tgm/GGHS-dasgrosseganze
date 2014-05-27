package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



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


@Entity
public class Zeit {
	@Id
	@GeneratedValue
	private Long ID;
	
	private Date anfang;
	
	private Date ende;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private DoodleEvent event;

	public Date getAnfang() {
		return anfang;
	}

	public void setAnfang(Date anfang) {
		this.anfang = anfang;
	}

	public Date getEnde() {
		return ende;
	}

	public void setEnde(Date ende) {
		this.ende = ende;
	}

	public DoodleEvent getEvent() {
		return event;
	}

	public void setEvent(DoodleEvent event) {
		this.event = event;
	}

	public Zeit(Date anfang, Date ende, DoodleEvent event) {
		super();
		this.anfang = anfang;
		this.ende = ende;
		this.event = event;
	}
	
	public Zeit(){}
	
}
