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

import com.example.myproject2.Variables;


@NamedQueries({
	@NamedQuery(name="getUsers",query="FROM User u order by u.ID"),
	@NamedQuery(name=Variables.GETUSER_BYID,query="FROM User u WHERE  u.ID = :id"),
	@NamedQuery(name=Variables.GETUSER_BYNAME,query="FROM User u WHERE  u.username like :id"),
})
@Entity
/**
 * @version 1
 * @Author Hannah Siegel, Daniel Herczeg (Doku)
 *
 * Stellt einen User dar
**/
public class User {
	@Id 
	@GeneratedValue
	private long ID;

	@Column(unique=true)
	private String username;

	@Email
	private String email;

	private String password;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy ="user")
	private Collection<DoodleNotification> notifications = new Vector <DoodleNotification>();  
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Erstellt einen neuen Standard-User
	**/
	public User(){
		this.username="User";
		this.email="no@email.com";
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 *
	 * Erstellt einen Benutzer mit den angegebenen Parametern
	**/
	public User(String username, String email,String password){
			this.username=username;
			this.email=email;
			this.password=password;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Der Name des Users
	 *
	 * Gibt den Namen des Users zurueck
	**/
	public String getUsername(){
		return this.username;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Die eMail
	 *
	 * Gibt die Mail des Benutzers zurueck
	**/
    public String geteMail(){
    	return this.email;
    }
    
    /**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Ein String, der den User beschreibt
	 *
	 * Gibt einen String zurueck, der den User beschreibt
	**/
	public String toString(){
		return "username:"+this.username+" - email:"+this.email;
	}
	
	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Das Passwort
	 *
	 * Gibt das Passwort des Users zurueck
	**/
	public String getPassword() {
		return password;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @param password    Das neue Passwort
	 *
	 * Setzt das Passwort des Users neu
	**/
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @author Hannah Siegel
	 * @version 1
	 * @return Die ID
	 *
	 * Gibt die ID des Users zurueck
	**/
	public Long getID(){
		return this.ID;
	}
}