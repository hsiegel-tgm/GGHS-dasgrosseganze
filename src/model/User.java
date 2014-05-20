package model;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
//Named Query
@NamedQueries({
	@NamedQuery(name="getUsers",query="FROM User"),
})

@Entity
public class User {
	@Id 
	@GeneratedValue
	long ID;

	String username;

	@Email
	String email;

	public User(){}

	public User(String username){
		this.username=username;
		this.email="no@email.com";
	}

	public User(String username, String email){
		this.username=username;
		boolean result = true;
		try {
			this.email=email;
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			//TODO Fehler Ausgabe bzw Exceptionhandling von Hannah!
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
}
