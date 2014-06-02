package model;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.example.myproject2.SendEmail;
import com.example.myproject2.Variables;




@NamedQueries({
	@NamedQuery(name=Variables.GETNOTIFICATION_BYUSERID,query="FROM DoodleNotification n WHERE n.user.ID = :id"), 
})
@Entity
@Table(name="notification")
public class DoodleNotification {

	@Id
	@GeneratedValue
	private Long ID;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private User user;
	
	private String text;
	
	private boolean geliefert;

	private Date geschriebenAm;

	
	public DoodleNotification(){
		
	}
	
	public DoodleNotification(User user, String text) {
		super();
		this.user = user;
		this.text = text;
		this.geliefert = false;
		this.geschriebenAm=new Date();
		try {
			SendEmail.send(user.geteMail(), "New Notification", text);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isGeliefert() {
		return geliefert;
	}

	public void setGeliefert(boolean geliefert) {
		this.geliefert = geliefert;
	}

	public Date getGeschriebenAm() {
		return geschriebenAm;
	}

	public void setGeschriebenAm(Date geschriebenAm) {
		this.geschriebenAm = geschriebenAm;
	}

	public Long getID() {
		return ID;
	}
	
	
	
}
