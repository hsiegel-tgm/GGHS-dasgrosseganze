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
	@NamedQuery(name=Variables.GETUSER_BYID,query="FROM User u WHERE  u.ID = :id"),
	@NamedQuery(name=Variables.GETUSER_BYNAME,query="FROM User u WHERE  u.username like :id"),
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

	private String password;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy ="user")
	private Collection<DoodleNotification> notifications = new Vector <DoodleNotification>();  
	
	public User(){
		this.username="User";
		this.email="no@email.com";
	}

	public User(String username, String email,String password){
			this.username=username;
			this.email=email;
			this.password=password;
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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getID(){
		return this.ID;
	}
	
}
