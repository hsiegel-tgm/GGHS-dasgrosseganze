package model;

import java.util.Collection;
import java.util.Vector;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;

/*
 * 
 * 2014-05-07 Hannah erstellt
 * 
 * 
 * TODO JUnit
 * TODO Komment
 * TODO Design pefekto
 * TODO GUI Test
 * TODO Variablen
 * TODO Coding style
 *  
 * */
//TODO named query get users must be ordered
//Named Query 
@NamedQueries({
	@NamedQuery(name="getUsers",query="FROM User u order by u.ID"),
	@NamedQuery(name="getSpecificUser,query="FROM User u WHERE  u.ID = :id"),

})


@Entity
public class User {
	@Id 
	@GeneratedValue
	private long ID;

	@Column(unique=true)
	private String username;

	@Email
	private String email;

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy ="user")
	private Collection<Notification> notifications = new Vector <Notification>();  
	
	public User(){
		this.username="User";
		this.email="no@email.com";
	}

	public User(String username){
		this.email="no@email.com";
		if(username != null && username != ""){
		this.username=username;
		}else{
			this.username ="User";
		}
	}

	public User(String username, String email){
		if(username != null  && username != ""){
			this.username=username;
		}else{
			this.username="User";
		}	
		if(email != null && email != ""){
		try {
			this.email=email;
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			//TODO Fehler Ausgabe bzw Exceptionhandling von Hannah!
			this.email="no@email.com";
		}
		}else{
			this.email="no@email.com";
		}
	}

	public String getUsername(){
		return this.username;
	}
    public String geteMail(){
    	return this.email;
    }
	public String toString(){
		return "username:"+this.username+" - email:"+this.email;
	}
	
	public Long getID(){
		return this.ID;
	}
	
}
